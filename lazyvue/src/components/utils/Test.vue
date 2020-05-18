<template>
    <div class="show-page">
        <div class="title"><h2>一些测试</h2></div>
        <hr/>
        <div class="bar">
            <at-input v-model="message" placeholder="输入消息" @change="changeMessage"></at-input>
        </div>
        <div class="bar">
            <at-textarea minRows="20" v-model="nodes" placeholder="消息展示位置"></at-textarea>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Test",
        data() {
            return {
                message: null,
                nodes: null,
            }
        },
        created() {
            this.initWebSocket()
        },
        methods: {
            initWebSocket() {
                const ws_url = "ws://127.0.0.1:9001/kanban"
                this.websock = new WebSocket(ws_url);
                this.websock.onmessage = this.wMessage;
                this.websock.onopen = this.wOpen;
                this.websock.onerror = this.wError;
                this.websock.onclose = this.wClose;
            },
            wMessage(message) {
                this.nodes +=  message
            },
            wOpen() {
                this.nodes += "链接尝试启动\n"
            },
            wError(e) {
                this.nodes += "链接失败\n"
                this.nodes += e
            },
            wClose() {
                this.nodes += "链接关闭\n"
            },
            changeMessage() {

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