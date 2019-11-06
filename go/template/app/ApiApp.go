package main

import (
	"github.com/elazarl/go-bindata-assetfs"
	"log"
	"myUtils/template/bean"
	"myUtils/template/constant"
	"myUtils/template/stat"
	"myUtils/template/utils"
	"net/http"
	"text/template"
)

var projectName = "/home/xc/works/projects/company/Oceanus/zdd-common"

var api = bean.Api{}
var isData = false

func run(w http.ResponseWriter, templates string) {
	utils.InitFile(projectName, constant.JAVA)
	api.BeanIn = utils.GetBean(api.In)
	api.BeanOut = utils.GetBean(api.Out)
	if t, err := template.ParseFiles(templates); err == nil {
		if err := t.Execute(w, api); err == nil {
			log.Println("生成完毕")
		} else {
			log.Fatal(err)
		}
	} else {
		log.Fatal(err)
	}
}

func initApi(r *http.Request) {
	projectName = r.PostFormValue("projectPath")
	api.Url = r.PostFormValue("api")
	api.In = r.PostFormValue("beanIn")
	api.Out = r.PostFormValue("beanOut")
	api.Des = r.PostFormValue("des")
	api.ProjectName = r.PostFormValue("projectName")
	api.CreateTime = r.PostFormValue("createTime")
}

func apis(w http.ResponseWriter, r *http.Request) {
	initApi(r)
	run(w, "template/ts/api")
}

func code(w http.ResponseWriter, r *http.Request) {
	initApi(r)
	codeTypes := r.PostFormValue("codeTypes")
	api.LowClass = r.PostFormValue("suf")
	api.Class = utils.UpperFirstWord(api.LowClass)
	run(w, "template/ts/"+codeTypes)
}

func main() {
	log.Println("初始化完成")
	log.Println("地址为: http://localhost:9090/")
	files := assetfs.AssetFS{
		Asset:     stat.Asset,
		AssetDir:  stat.AssetDir,
		AssetInfo: stat.AssetInfo,
		Prefix:    "dist",
	}
	http.HandleFunc("/apis", apis)
	http.HandleFunc("/code", code)
	if isData {
		http.Handle("/", http.FileServer(&files))
	} else {
		http.Handle("/", http.FileServer(http.Dir("./template/dist/")))
	}
	err := http.ListenAndServe(":9090", nil)
	if err != nil {
		log.Fatal("Listen: ", err)
	}
}
