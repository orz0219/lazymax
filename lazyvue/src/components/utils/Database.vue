<template>
    <div class="show-page">
        <div class="title"><h2>entity 自动生成</h2></div>
        <hr/>
        <div class="bar">
            <at-input v-model="dbConfig.ip" placeholder="ip:port" @change="cacheProject"/>
            <at-input v-model="dbConfig.user" placeholder="用户名" @change="cacheProject"></at-input>
            <at-input v-model="dbConfig.password" type="password" placeholder="密码" @change="cacheProject"></at-input>
            <at-input v-model="dbConfig.dbName" placeholder="数据库<不是表,是库!>" @change="cacheProject"></at-input>
            <at-select v-model="tableName" v-if="tableNames.length > 0" filterable size="large" @on-change="changeTables">
                <at-option :key="index" v-for="(item, index) in tableNames" :value="item">{{item}}</at-option>
            </at-select>
            <at-input v-model="className" v-if="className != null" placeholder="class类名"></at-input>
            <div style="text-align: center">
                <at-button type="success" @click="getIt">生成</at-button>
            </div>
        </div>
        <div class="bar">
            <at-textarea minRows="20" v-model="nodes" placeholder="文档展示位置"></at-textarea>
        </div>
        <div class="bar">
            <at-textarea minRows="1" v-model="sqlStr" placeholder="数据库字段对应"></at-textarea>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Api",
        data() {
            return {
                dbConfig:{
                    ip: null,
                    user: null,
                    password: null,
                    dbName: null,
                },
                tableNames: [],
                tableName: null,
                result: null,
                nodes: null,
                sqlStr: null,
                className: null
            }
        },
        created() {
            let dbConfig = localStorage.getItem("cacheDb")
            if (dbConfig) {
                this.dbConfig = eval("(" + dbConfig + ")")
            }
        },
        methods: {
            getIt() {
               for (let m in this.dbConfig){
                   let value = this.dbConfig[m]
                   if (null == value || "" === value) {
                       this.$Message("所有参数不得为空!")
                       return
                   }
               }
                this.$post("/db", this.dbConfig, result=>{
                    result = result.data
                    this.result = result
                    this.tableNames = Object.keys(result)
                })
            },
            cacheProject() {
                localStorage.setItem("cacheDb", JSON.stringify(this.dbConfig))
            },
            changeTables() {
                let tableName = this.tableName
                const fields = this.result[tableName]
                tableName = tableName.charAt(0).toUpperCase() + tableName.slice(1)
                this.className = tableName.replace(/_(\w)/g, function(all, letter){
                    return letter.toUpperCase();
                })
                let str = ''
                let sqlStr = ''
                for (let index = 0; index < fields.length; index++) {
                    let field = fields[index]
                    let upCaseName = field['Name'].replace(/_(\w)/g, function(all, letter){
                        return letter.toUpperCase();
                    })
                    sqlStr += field['Name'] + " " + upCaseName
                    if (index !== fields.length - 1) {
                        sqlStr += ","
                    }
                    str += '// ' + field['Comment'].replace(/ /g, '_') + '\n'
                    let type = field['Type']
                    let types = 'String'
                    if (type === 'bigint') {
                        types = 'Long'
                    } else if (type === 'int' || type === 'tinyint') {
                        types = 'Integer'
                    } else if (type === 'date' || type === 'datetime') {
                        types = 'Date'
                    }
                    str += 'private ' + types + ' ' + upCaseName + ';\n'
                }
                this.nodes = str
                this.sqlStr = sqlStr
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