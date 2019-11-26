<template>
    <div class="show-page">
        <div class="title"><h2>setter自动生成</h2></div>
        <hr/>
        <div class="bar">
            <at-input v-model="message.projectPath" placeholder="输入项目路径" @change="cacheProject"/>
            <at-input v-show="message.setterTypes !== 'setter_short'" v-model="message.beanIn" placeholder="输入入参类名" @change="cacheProject"></at-input>
            <at-input  v-model="message.beanOut" placeholder="输入出参类名" @change="cacheProject"></at-input>
            <at-radio-group v-model="message.setterTypes">
                <at-radio-button label="setter_short">获取缩写对象</at-radio-button>
                <at-radio-button label="setter_out_in">出参-入参互转</at-radio-button>
            </at-radio-group>
            <at-collapse v-show="message.setterTypes === 'setter_out_in'" class="no-margin-top">
                <at-collapse-item title="请进行出入参比对">
                    <div style="text-align: right; margin-bottom: 10px">
                        <at-button type="primary" @click="post">刷新</at-button>
                    </div>
                    <div class="row at-row no-gutter">
                        <div class="col-md-12" ref="inTable">
                            <at-table :columns="setterTableColumns1" :data="outToIns.ins" stripe></at-table>
                        </div>
                        <div class="col-md-12">
                            <at-table :columns="setterTableColumns2" :data="outToIns.outs" stripe></at-table>
                        </div>
                    </div>
                </at-collapse-item>
            </at-collapse>
            <div class="submit-bar" style="text-align: center">
                <at-button type="success" @click="getIt">生成</at-button>
            </div>
        </div>
        <div class="bar">
            <div class="row at-row no-gutter">
                <div v-show="message.setterTypes !== 'setter_short'" class="col-md-12">
                    <at-textarea minRows="20" v-model="nodes0str" placeholder="入参展示位置"></at-textarea>
                </div>
                <div v-show="message.setterTypes === 'setter_short'" class="col-md-24">
                    <at-textarea minRows="20" v-model="nodes1" placeholder="出参展示位置"></at-textarea>
                </div>
                <div v-show="message.setterTypes !== 'setter_short'" class="col-md-12">
                    <at-textarea minRows="20" v-model="nodes2str" placeholder="出参展示位置"></at-textarea>
                </div>
            </div>
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
                    setterTypes: "setter_short"
                },
                outToIns: {
                    ins: [],
                    insChecked: 0,
                    outs: [],
                    outsChecked: 0
                },
                nodes0str: null,
                nodes1: null,
                nodes2str: null,
                setterTableColumns1: [
                    //<at-input-number v-model="num"></at-input-number>
                    {title: '选择', render: (h, params) => {
                        return h('at-input-number',{
                                on: {
                                    change() {
                                        // eslint-disable-next-line no-console
                                        console.log(params.index)
                                    }
                                }
                            }
                        )}
                    },
                    {title: '序号', key: 'index'},
                    {title: '名称', key: 'Name'},
                    {title: '注释', key: 'Comment'}
                ],
                setterTableColumns2: [
                    {title: '序号', key: 'index'},
                    {title: '名称', key: 'Name'},
                    {title: '注释', key: 'Comment'},
                    {title:'移除', render: (h, params) => {
                            return h('at-button',{
                                props:{
                                    icon: "icon-x",
                                    circle: true
                                },
                                on: {
                                    click: () =>{
                                        this.outToIns.outs.splice(params.item.index, 1)
                                    }
                                }
                            })
                    }}
                ],
                test1:[
                    {
                        title: '姓名',
                        key: 'name'
                    },
                    {
                        title: '年龄',
                        key: 'age'
                    },
                    {
                        title: '地址',
                        key: 'address'
                    }
                ],
                test2:[
                    {
                        name: '库里',
                        age: 18,
                        address: '深圳市宝安区创业一路'
                    },
                    {
                        name: '詹姆斯',
                        age: 25,
                        address: '广州市天河区岗顶'
                    },
                    {
                        name: '科比',
                        age: 24,
                        address: '上海市浦东新区'
                    },
                    {
                        name: '杜兰特',
                        age: 22,
                        address: '深圳市南山区深南大道'
                    }
                ]
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
                if (this.message.setterTypes === "setter_out_in") {
                    return
                }
                this.post()
            },
            post() {
                this.$post("/setter", this.message, result=>{
                    let data = result.data
                    if (data && data['In']) {
                        for (let index = 0; index < data['In'].length; index++) {
                            data['In']['Index'] = index
                        }
                        for (let index = 0; index < data['Out'].length; index++) {
                            data['Out']['Index'] = index
                        }
                        this.outToIns = {
                            ins: data['In'],
                            insChecked: 0,
                            outs: data['Out'],
                            outsChecked: 0
                        }
                    } else {
                        this.nodes1 = data
                    }
                })
            },
            cacheProject() {
                localStorage.setItem("cacheMessage", JSON.stringify(this.message))
            },
            changeChecked(arr) {
                this.$Message(arr)
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
    .bar .at-input, .bar .at-radio-group, .bar .submit-bar {
        margin-top: 10px;
    }
</style>