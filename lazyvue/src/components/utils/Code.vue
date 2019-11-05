<template>
    <div class="show-page">
        <div class="title"><h2>代码自动生成</h2></div>
        <hr/>
        <div class="bar">
            <at-input v-model="message.projectPath" placeholder="输入项目路径" @change="cacheProject"/>
            <at-input v-model="message.api" placeholder="输入api参数" @change="cacheProject"></at-input>
            <at-input v-model="message.projectName" placeholder="输入api参数" @change="cacheProject"></at-input>
            <at-input v-model="message.des" placeholder="输入模块注释[例: 首页模块]" @change="cacheProject"></at-input>
            <at-input v-model="message.suf" placeholder="输入获取方法前缀小写[例: home]" @change="cacheProject"></at-input>
            <at-input v-model="message.beanIn" placeholder="输入获取方法入参类名" @change="cacheProject"></at-input>
            <at-input v-model="message.beanOut" placeholder="输入获取方法出参类名" @change="cacheProject"></at-input>
            <at-radio-group v-model="message.codeTypes">
                <at-radio-button label="rpc">rpc</at-radio-button>
                <at-radio-button label="service">service</at-radio-button>
                <at-radio-button label="serviceImpl">serviceImpl</at-radio-button>
                <at-radio-button label="rmt">rmt</at-radio-button>
                <at-radio-button label="rmtImpl">rmtImpl</at-radio-button>
                <at-radio-button label="mount">mount</at-radio-button>
            </at-radio-group>
            <div style="text-align: center">
                <at-button type="success" @click="getIt">生成</at-button>
            </div>
        </div>
        <div class="bar">
            <at-textarea minRows="20" v-model="nodes" placeholder="代码展示位置"></at-textarea>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Code",
        data() {
            return {
                message:{
                    projectPath: null,
                    api: null,
                    des: null,
                    suf: null,
                    codeTypes: 'rpc',
                    projectName: null,
                    beanIn: null,
                    beanOut: null,
                },
                nodes: null
            }
        },
        created() {
            let message = localStorage.getItem("cacheMessage")
            if (message) {
                // eslint-disable-next-line no-debugger
                debugger
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
                this.$post("/code", this.message, result=>{
                    this.nodes = result.data
                })
            },
            cacheProject: function () {
                let message = this.message
                let api = message.api
                if (api) {
                    let projectNames = api.split("/")
                    // eslint-disable-next-line no-console
                    if(projectNames[2]) {
                        this.message.projectName = projectNames[2]
                    }
                }
                localStorage.setItem("cacheMessage", JSON.stringify(this.message))
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