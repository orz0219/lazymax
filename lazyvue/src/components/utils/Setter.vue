<template>
    <div class="show-page">
        <div class="title"><h2>setter自动生成</h2></div>
        <hr/>
        <div class="bar">
            <at-input v-model="message.projectPath" placeholder="输入项目路径" @change="cacheProject"/>
            <at-input v-model="message.beanIn" placeholder="输入入参类名" @change="cacheProject"></at-input>
            <at-input v-model="message.beanOut" placeholder="输入出参类名" @change="cacheProject"></at-input>
            <at-radio-group v-model="message.setterTypes">
                <at-radio-button label="normal">normal</at-radio-button>
                <at-radio-button label="list">list</at-radio-button>
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
        name: "Setter",
        data() {
            return {
                message:{
                    projectPath: null,
                    beanIn: null,
                    beanOut: null,
                    setterTypes: "normal"
                },
                nodes: null
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
                this.$post("/setter", this.message, result=>{
                    this.nodes = result.data
                })
            },
            cacheProject() {
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