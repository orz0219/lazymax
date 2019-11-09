<template>
    <div class="show-page">
        <div class="title"><h2>更新日志</h2></div>
        <hr/>
        <div class="bar">
            <at-textarea minRows="20" v-model="nodes" placeholder="更新日志"></at-textarea>
        </div>
    </div>
</template>

<script>
    export default {
        name: "History",
        data() {
            return {
                nodes: null
            }
        },
        created() {
          this.nodes =
              "2019年11月08日  增加后缀注释的识别\n"+
              "2019年11月08日  增加更新日志功能"
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