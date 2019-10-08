const express = require("express");
const router = express.Router();

// const request = require("request");
const request = require("request-promise");
const cheerio = require("cheerio");

const models = require("../models");

router.get("/test", function(req, res){
    
    res.write("Success");
    res.end();
});

router.get("/", function(req, res){

    const URL = "https://www.concepts1one.co.kr";

    let SEARCH = "슬랙스";
   
    const option = {
        uri: `${URL}/shop/search_result.php`,
        method: "GET",
        qs: {
            search_str: SEARCH
        }
    }

    const product = {
        searchName: SEARCH,
        productName: "",
        salePrice: 0,
        costPrice: 0
    }

    const productArr = new Array();

    (async () => {
        let firstNum = 0;
        let secondNum = 0;

        await request(option, function(error, response, body){

            let $ = cheerio.load(body);
            let divSearch = $("div.prd_search");

            if(divSearch.find("div.info").length > 0) {
                // 처음 기본 메인 화면 데이터
                divSearch.find("div.info").each(function(index, elem) {
                    product.productName = $(this).find(".name a").text();
                    product.salePrice = $(this).find(".price").find(".sell").text().split(",").join("");
                    product.costPrice = $(this).find(".price").find(".consumer").text().split(",").join("");
                    productArr.push(product);
                });

                let btn = $("#btn_more").find("a").text().split(" ")[1];
                firstNum = parseInt(btn.split("/")[0]);
                secondNum = parseInt(btn.split("/")[1]);
            }
        }); // end request    
    
        // 페이징 처리 부분 데이터
        for(let i=2; i<secondNum-firstNum+2; i++) {
            const option = {
                uri: `${URL}/main/exec.php`,
                method: "GET",
                qs: {
                    exec_file: "skin_module/skin_ajax.php",
                    obj_id: "prd_basic",
                    _tmp_file_name: "shop/search_result.php",
                    single_module: "search_result_prd_list",
                    striplayout: 1,
                    module_page: i,
                    document_url: `https://www.concepts1one.co.kr/shop/search_result.php?search_str=${SEARCH}`,
                    search_str: SEARCH
                }
            } // option
            console.log(1)
            await request(option, function(error, response, body) {
            
                console.log(0)
                const $ = cheerio.load(JSON.parse(body).content);
                
                divSearch = $("li .box");

                // 페이징 데이터
                divSearch.find(".info").each(function(index, elem) {
                    product.productName = $(this).find(".name a").text();
                    product.salePrice = parseInt($(this).find(".price").find(".sell").text().split(",").join("").split(" ")[0]);
                    product.costPrice = parseInt($(this).find(".price").find(".consumer").text().split(",").join("").split(" ")[0]);
                    productArr.push(product);
                });
            }); // request
        } // end for
        console.log(productArr);
        res.write("Index Page");
        res.end();
    })();

    


    

});

module.exports = router;