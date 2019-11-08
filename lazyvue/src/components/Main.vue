<template>
    <div class="container">
        <div class="app">
            <img src="../assets/logo.png" alt="">
            <div class="title">慵懒的开发工具集-新流通版</div>
            <at-button type="text" @click="url('/api')">文档生成</at-button>
            <at-button type="text" @click="url('/rpc')">代码生成</at-button>
            <at-button type="text" @click="url('/setter')">Setter生成</at-button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Main",
        methods: {
            url (url) {
                this.$router.push(url)
            }
        }
    }
</script>

<style scoped>
    .container {
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        font-family: -apple-system, BlinkMacSystemFont, "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
    }
    .app {
        color: #2c3e50;
        text-align: center;
    }
    .title {
        font-size: 10pt;
        font-weight: normal;
        margin: 40px 0 10px 0;
    }
    img {
        width: 250px;
        height: 80px;
    }
</style>