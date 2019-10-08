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
    const SEARCH = "슬랙스";

    (async () => {
        // 검색어 count
        let searchCount = await models.Searchs.count({
            where: {searchname: SEARCH}
        }).then((results)=> {
           return results;
        });
        const productArr = new Array();

        // 존재하지 않는 검색어일 경우 데이터를 확인하고 데이터가 존재할 경우 검색어 저장 AND 검색 결과 데이터도 저장
        if(searchCount === 0) {
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
            
            
            // 몇개의 페이지가 구성되어있는지 확인하는 request
            await request(option, function(error, response, body){

                if(error) throw error;

                let $ = cheerio.load(body);
                let divSearch = $("div.prd_search");
                const checkData = divSearch.find(".empty").text();

                console.log(checkData);

                // 데이터가 존재할 경우
                if(checkData === ""){
                    // 데이터가 몇페이지 인지 확인
                    let btn = $("#btn_more").find("a").text().split(" ")[1];
                    firstNum = parseInt(btn.split("/")[0]);
                    secondNum = parseInt(btn.split("/")[1]);
                } else {
                    firstNum = 0;
                    secondNum = 0;
                }
            }); // end request    

            // 데이터가 존재하는 경우
            if(firstNum !== 0 && secondNum !== 0) {
                // 데이터 파싱 부분
                for(let i=1; i<secondNum-firstNum+2; i++) {
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

                    await request(option, function(error, response, body) {
                    
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

            } // end if
        } // end if
        res.send(productArr);
        res.end();
    })();
}); // router

module.exports = router;