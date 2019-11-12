<template>
    <div class="show-page">
        <div class="title"><h2>文档自动生成</h2></div>
        <hr/>
        <div class="bar">
            <at-input v-model="message.projectPath" placeholder="输入项目路径" @change="cacheProject"/>
            <at-input v-model="rpc" placeholder="输入rpc/controller层类名" @change="changeRpc"></at-input>
            <at-input v-model="message.api" placeholder="输入api参数" @change="cacheProject"></at-input>
            <at-input v-model="message.beanIn" placeholder="输入入参类名" @change="cacheProject"></at-input>
            <at-input v-model="message.beanOut" placeholder="输入出参类名" @change="cacheProject"></at-input>
            <at-radio-group v-model="apiIndex" :key="index" v-for="(item, index) in apis" @radio-group-change="changeApi">
                <at-radio-button :label="index">{{item.Url}}</at-radio-button>
            </at-radio-group>
            <div style="text-align: center">
                <at-button type="success" @click="getIt">生成</at-button>
            </div>
        </div>
        <div class="bar">
            <at-textarea minRows="20" v-model="nodes" placeholder="文档展示位置"></at-textarea>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Api",
        data() {
            return {
                message:{
                    projectPath: null,
                    api: null,
                    beanIn: null,
                    beanOut: null,
                },
                rpc: null,
                nodes: null,
                apis:[],
                apiIndex: -1
            }
        },
        created() {
            let message = localStorage.getItem("cacheMessage")
            if (message) {
                this.message = eval("(" + message + ")")
            }
        },
        methods: {
            getIt() {
               for (let m in this.message){
                   let value = this.message[m]
                   if (null == value || "" === value) {
                       this.$Message("所有参数不得为空!")
                       return
                   }
               }
                this.$post("/apis", this.message, result=>{
                    let nodes = result.data
                    this.nodes = nodes.replace(/</g, "&lt;")
                })
            },
            cacheProject() {
                localStorage.setItem("cacheMessage", JSON.stringify(this.message))
            },
            changeRpc() {
                let rpc = this.rpc
                if (rpc) {
                    this.rpc = rpc.trim()
                } else {
                    return
                }
                this.$post("/apis/rpc", {rpc: rpc, projectPath: this.message.projectPath}, result=>{
                    let r = result.data
                    if (r) {
                        this.apis = r;
                    }
                })
            },
            changeApi(index) {
                let apiMessage = this.apis[index]
                this.message.api = apiMessage.Url
                this.message.beanIn = apiMessage.In
                this.message.beanOut = apiMessage.Out
            }
        }
    }
</script>

<style scoped>
    .show-page{
        width: 100%;
        height: 100%;
        background-color: #f6f6f6;
        box-shadow: 0 4px 30px 0 rgba(223,225,230,0.5);
        padding: 40px;
    }
    .title{
        text-align: center;
    }
    .bar{
        position: relative;
        padding: 32px;
        border: 1px solid #e2ecf4;
        border-radius: 4px 4px 0 0;
        background-color: #fff;
    }
    .bar div{
        margin-top: 10px;
    }
</style>