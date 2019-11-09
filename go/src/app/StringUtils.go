package main

import (
	"regexp"
	"strings"
)

// 获取默认值
func getDefaultValue(_type string) string {
	switch _type {
	case "String":
		return "someString"
	case "Long":
		return "1572262421000"
	case "Integer":
		return "1"
	case "String[]":
		return "[\"someString\",\"someString\",\"someString\"]"
	case "Boolean":
		return "false"
	}
	return ""
}

//获取字段信息
func FillField(line string, _field Field) Field {
	//注解类型1
	if r, _ := regexp.Compile("\\s+//\\s*"); r.MatchString(line) {
		_field.Comment = r.ReplaceAllString(line, "")
	}
	//注解类型2
	if r, _ := regexp.Compile("//\\s*\\S+"); r.MatchString(line) {
		_field.Comment = strings.Replace(r.FindString(line), "//", "", -1)
		line = r.ReplaceAllString(line, "")
	}

	if r, _ := regexp.Compile("\\s+@ValidateNull"); r.MatchString(line) {
		_field.AllowNull = "否"
	}
	if r, _ := regexp.Compile("\\s+private\\s+"); r.MatchString(line) {
		arr := strings.Split(r.ReplaceAllString(line, ""), " ")
		_field.Name = strings.TrimSpace(strings.Replace(arr[1], ";", "", -1))
		_field.Type = strings.TrimSpace(arr[0])
		_field.DefaultValue = getDefaultValue(_field.Type)
	}
	return _field
}

func UpperFirstWord(word string) string {
	runes := []rune(word)
	runes[0] -= 32
	return string(runes)
}
