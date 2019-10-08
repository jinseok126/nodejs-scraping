module.exports = function(sequelize, DataType){
    return sequelize.define("product", {
        idx: {
            type: DataType.INTEGER,
            primaryKey: true,
            autoIncrement: true,
            comment: "인덱스"
        },
        searchIdx: {
            type: DataType.INTEGER,
            allowNull: false,
            field: "search_idx",
            comment: "검색어 인덱스"
        },
        productName: {
            type: DataType.STRING,
            unique: true,
            defaultValue: 0,
            field: "product_name",
            comment: "상품 이름"
        },
        salePrice: {
            type: DataType.INTEGER,
            defaultValue: 0,
            field: "sale_price",
            comment: "세일 가격"
        },
        costPrice: {
            type: DataType.INTEGER,
            defaultValue: 0,
            field: "cost_price",
            comment: "세일 가격"
        }
    }, {
        timestamps: true,
        underscore: true,
        freezeTableName: true,
        tableName: "product_tbl",
        comment: "상품정보 테이블"
    });
}