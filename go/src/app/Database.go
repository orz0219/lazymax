package main

import (
	"database/sql"
	"encoding/json"
	"fmt"
	_ "github.com/Go-SQL-Driver/MySQL"
	"log"
)

var db *sql.DB

type DbConfig struct {
	ip string
	user string
	password string
	dbName string
	tableName string
}

var dbConfig DbConfig

var dbMap map[string][]Field

func con()  {
	var err error
	db, err = sql.Open("mysql", dbConfig.user+":"+dbConfig.password+"@tcp("+dbConfig.ip+")/"+dbConfig.dbName)
	checkErr(err)
}


func connect() map[string][]Field{
	con()
	tableNames := getTables()
	dbMap = make(map[string][]Field )
	for _, tableName := range tableNames {
		dbMap[tableName] = getFieldsInTable(tableName)
	}
	err := db.Close()
	checkErr(err)
	return dbMap
}

func getTables() []string {
	sqlStr := fmt.Sprintf("show tables")
	println(sqlStr)
	rows, err := db.Query(sqlStr)
	checkErr(err)
	var names []string
	for rows.Next() {
		var name sql.NullString
		err := rows.Scan(&name)
		checkErr(err)
		names = append(names, name.String)
	}
	return names
}

func getFieldsInTable(tableName string) []Field {
	sqlStr := fmt.Sprintf("select column_name, data_type, column_comment from information_schema.columns where table_name = '%s' and table_schema = '%s'", tableName, dbConfig.dbName)
	println(sqlStr)
	rows, e := db.Query(sqlStr)
	checkErr(e)
	var fields []Field
	for rows.Next(){
		var field Field
		_ = rows.Scan(&field.Name, &field.Type, &field.Comment)
		fields = append(fields, field)
	}
	return fields
}

func selectAllMessageByTableName() {
	con()
	sqlStr := "show table status"
	rows, e := db.Query(sqlStr)
	checkErr(e)
	columns, _ := rows.Columns()
	bytes, e := json.Marshal(columns)
	checkErr(e)
	println(string(bytes))
	values := make([]interface{}, len(columns))
	values1 := make([]interface{}, len(columns))
	for rows.Next(){
		for i := range columns {
			values1[i] = &values[i]
		}
		_ = rows.Scan(values1...)
		b, _ := json.Marshal(values)
		println(string(b))
	}

}

func checkErr(err error)  {
	if err != nil{
		log.Fatal(err)
	}
}
