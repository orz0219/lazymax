package main

import (
	"log"
	"net/http"
	"strings"
	"text/template"
)

var projectName = "/home/xc/works/projects/company/Oceanus/zdd-common"

var api = Api{}

var funcMap = template.FuncMap{"add": add}

//模板计算
func add(index int) int {
	return index + 1
}

//跨域问题
func cross(w http.ResponseWriter) {
	w.Header().Set("Access-Control-Allow-Origin", "*")             //允许访问所有域
	w.Header().Add("Access-Control-Allow-Headers", "Content-Type") //header的类型
}

func run(w http.ResponseWriter, templates string, t string) {
	InitFile(projectName, JAVA)
	api.BeanIn = GetBean(api.In)
	api.BeanOut = GetBean(api.Out)
	if t, err := template.New(t).Funcs(funcMap).ParseFiles(templates); err == nil {
		if err := t.Execute(w, api); err == nil {
			log.Println("生成完毕")
		} else {
			log.Fatal(err)
		}
	} else {
		log.Fatal(err)
	}
}

func initApi(r *http.Request, w http.ResponseWriter) {
	cross(w)
	projectName = r.PostFormValue("projectPath")
	api.Url = r.PostFormValue("api")
	api.In = r.PostFormValue("beanIn")
	api.Out = r.PostFormValue("beanOut")
	api.Des = r.PostFormValue("des")
	api.ProjectName = r.PostFormValue("projectName")
	api.CreateTime = r.PostFormValue("createTime")
}

func apis(w http.ResponseWriter, r *http.Request) {
	initApi(r, w)
	run(w, "src/ts/api", "api")
}

func apisRpc(w http.ResponseWriter, r *http.Request)  {
	initApi(r, w)
	api.Rpc = strings.TrimSpace(r.PostFormValue("rpc"))
	InitFile(projectName, JAVA)
	_, err := w.Write(parseRpc())
	if err != nil {
		log.Fatal(err)
	}
}

func code(w http.ResponseWriter, r *http.Request) {
	initApi(r, w)
	codeTypes := r.PostFormValue("codeTypes")
	api.LowClass = r.PostFormValue("suf")
	api.Class = UpperFirstWord(api.LowClass)
	run(w, "src/ts/"+codeTypes, codeTypes)
}

func setter(w http.ResponseWriter, r *http.Request) {
	cross(w)
	projectName = r.PostFormValue("projectPath")
	api.Out = r.PostFormValue("beanOut")
	api.In = r.PostFormValue("beanIn")
	InitFile(projectName, JAVA)
	types := r.PostFormValue("setterTypes")
	if types == "setter_short" {
		_, err := w.Write([]byte(short()))
		if err != nil {
			log.Fatal(err)
		}
	} else if types == "setter_out_in" {
		bytes, errs := outToIn()
		if errs != nil {
			log.Fatal(errs)
		}
		_, err := w.Write(bytes)
		if err != nil {
			log.Fatal(err)
		}
	}

}

func main() {
	log.Println("初始化完成")
	log.Println("地址为: http://localhost:9090/")

	http.HandleFunc("/apis", apis)
	http.HandleFunc("/apis/rpc", apisRpc)
	http.HandleFunc("/code", code)
	http.HandleFunc("/setter", setter)
	http.Handle("/", http.FileServer(http.Dir("./src/dist/")))

	err := http.ListenAndServe(":9090", nil)
	if err != nil {
		log.Fatal("Listen: ", err)
	}
}
