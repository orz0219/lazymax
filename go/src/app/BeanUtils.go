package main

import (
	"bytes"
	"encoding/json"
	"io/ioutil"
	"log"
	"math/rand"
	"regexp"
	"strings"
)

var column = "    "
var fileCache = make(map[string]string)
var otherBeans []Bean
var cacheBeans []string
var isList = false
var basics = []string{"String", "Integer", "Boolean", "Long"}

func GetBean(fileName string) Bean {
	isList = false
	isList, fileName = checkList(fileName)
	if isBasic, b := checkType(fileName); isBasic {
		return b
	}
	cacheBeans = []string{"EventVOOut"}
	content := fileCache[fileName]
	split := strings.Split(content, "\n")
	var _bean = Bean{}
	for _, s := range split {
		if r, _ := regexp.Compile("\\s+\\*\\s*@description\\s*:\\s*"); r.MatchString(s) {
			description := r.ReplaceAllString(s, "")
			if isList {
				_bean.Name = "List<" + fileName + "> " + description
			} else {
				_bean.Name = fileName + " " + description
			}
			break
		}
	}
	_bean.Fields = getFields(content)
	_bean.Model = getModelString(_bean.Fields, column)
	_bean.OtherBeans = otherBeans
	otherBeans = nil
	return _bean
}

func getFields(content string) []Field {
	var fields []Field
	_field := Field{AllowNull: "是"}
	split := strings.Split(content, "\n")
	for _, s := range split {
		_field = FillField(s, _field)
		if _field.Name != "" {
			fields = append(fields, _field)
			_field = Field{AllowNull: "是"}
		}
	}
	return fields
}

func getModelString(fields []Field, c string) string {
	var buffer bytes.Buffer
	if c != column {
		buffer.WriteString(column)
		buffer.WriteString(column)
	}
	if isList {
		buffer.WriteString("[{\n" + c)
	} else {
		buffer.WriteString("{\n" + c)
	}
	for index, field := range fields {
		buffer.WriteString(column)
		buffer.WriteString("\"")
		buffer.WriteString(field.Name)
		buffer.WriteString("\"")
		buffer.WriteString(": ")
		_type := field.Type
		isExist := false
		for _, f := range cacheBeans {
			if f == _type {
				isExist = true
				break
			}
		}
		cacheBeans = append(cacheBeans, _type)
		_content, isOk := fileCache[_type]
		switch {
		//处理对象
		case isOk:
			_fields := getFields(_content)
			if !isExist {
				otherBeans = append(otherBeans, Bean{Name: _type, Fields: _fields})
			}
			buffer.WriteString(getModelString(_fields, c+column+column))
		case _type == "String":
			buffer.WriteString("\"")
			buffer.WriteString(field.DefaultValue)
			buffer.WriteString("\"")
		case strings.HasPrefix(_type, "List"):
			buffer.WriteString("[\n" + c)
			r, _ := regexp.Compile("[a-zA-Z]+")
			if r != nil {
				listBeanName := r.FindAllString(_type, 2)[1]
				content := fileCache[listBeanName]
				_fields := getFields(content)
				if !isExist {
					otherBeans = append(otherBeans, Bean{Name: _type, Fields: _fields})
				}
				buffer.WriteString(getModelString(_fields, c+column+column))
			}
			buffer.WriteString("\n" + column + c + "]")
		default:
			buffer.WriteString(field.DefaultValue)
		}
		if index != len(fields)-1 {
			buffer.WriteString(",")
		}
		buffer.WriteString("\n" + c)
	}
	if isList {
		buffer.WriteString("}]")
	} else {
		buffer.WriteString("}")
	}
	return buffer.String()
}

// 初始化获取所有类的类信息
func InitFile(path string, types SuffixType) {
	suffixType := string(types)
	if files, err := ioutil.ReadDir(path); err == nil {
		for _, file := range files {
			fileName := file.Name()
			if file.IsDir() {
				InitFile(path+"/"+fileName, types)
			} else if strings.HasSuffix(fileName, suffixType) {
				fileNameMsg := regexp.MustCompile(`(\S+)` + suffixType).FindStringSubmatch(fileName)
				shortFileName := fileNameMsg[1]
				if _, err := GetBeanType(shortFileName); err == nil {
					// 读取文件字符串并缓存
					if fileByte, err := ioutil.ReadFile(path + "/" + fileName); err == nil {
						fileCache[shortFileName] = string(fileByte)
					} else {
						log.Println(err)
					}
				} else {
					//log.Println(fileName, err)
				}
			}
		}
	} else {
		log.Println(err)
	}
}

func checkType(fileName string) (bool, Bean) {
	for _, name := range basics {
		if name == fileName {
			if isList {
				name = "List<" + fileName + ">"
			}
			b := Bean{
				Name: name,
				Fields: []Field{{
					Name:         "无",
					Type:         fileName,
					AllowNull:    "是",
					Comment:      "无",
					DefaultValue: "",
				}},
				OtherBeans: nil,
				Model:      "baseType",
			}
			return true, b
		}
	}
	return false, Bean{}
}

func checkList(fileName string) (bool, string) {
	if strings.HasPrefix(fileName, "List<") {
		r, _ := regexp.Compile("[a-zA-Z]+")
		return true, r.FindAllString(fileName, 2)[1]
	}
	return false, fileName
}

var shortArr map[string]string

func short() string {
	shortArr = make(map[string]string)
	fileName := api.Out
	isList, _ := checkList(fileName)
	if isList {
		return "不支持集合"
	}
	for _, types := range basics {
		if types == fileName {
			return "不支持基本类型"
		}
	}
	fields := getFields(fileCache[fileName])
	var buffer bytes.Buffer
	for _, field := range fields {
		comment := field.Comment
		name := field.Name
		types := field.Type
		if strings.TrimSpace(comment) != "" {
			buffer.WriteString("    // " + comment + "\n")
		}
		buffer.WriteString("    private " + types + " " + shortName(name) + "\n\n")
	}
	return buffer.String()
}

func shortName(name string) string {
	name = strings.ToLower(name)
	if len(name) > 4 {
		name = checkRepeat(name)
	}
	shortArr[name] = name
	return name
}

var words = []string{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"}

func checkRepeat(name string) string {
	var shortName string
	if len(name) < 5 {
		shortName = name[0:] + words[rand.Intn(14)] + name[:len(name)-1]
	} else {
		i := rand.Intn(len(name) - 1)
		shortName = name[:1] + name[i:i+1] + name[len(name)-1:]
	}

	if shortArr[shortName] == shortName {
		return checkRepeat(name)
	}
	return shortName
}

type outToInResult struct {
	In  []Field
	Out []Field
}

func outToIn() ([]byte, error) {
	in := api.In
	out := api.Out
	resut := outToInResult{
		In:  getFields(fileCache[in]),
		Out: getFields(fileCache[out]),
	}
	return json.Marshal(resut)
}
