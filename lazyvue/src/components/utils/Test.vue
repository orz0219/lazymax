<template>
    <div class="show-page">
        <div class="title"><h2>测试专区</h2></div>
        <hr/>
        <at-tabs v-model="activeKey">
            <at-tab-pane label="数据管理" name="dbs">
                <p>Content of Tab Pane 1</p>
            </at-tab-pane>
            <at-tab-pane label="Tab2" name="name2">
                <p>Content of Tab Pane 2</p>
            </at-tab-pane>
            <at-tab-pane label="Tab3" name="name3">
                <p>Content of Tab Pane 3</p>
            </at-tab-pane>
        </at-tabs>
    </div>
</template>

<script>
    export default {
        name: "Test",
        data() {
            return {
                activeKey: "dbs"
            }
        },
        created() {
            this.$post("/test", {}, result => {
                // eslint-disable-next-line no-console
                console.log(result)
            })
        },
        methods: {
            changeTablesName() {

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