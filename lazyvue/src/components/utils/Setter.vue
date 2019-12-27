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
                                    blur: (a) => {
                                        let ins = this.outToIns.ins
                                        let old_index = params.index
                                        let new_index = a.target.valueAsNumber - 1
                                        if (new_index > ins.length || new_index === 0) {
                                            a.target.value = 0
                                            return
                                        }
                                        let temp = ins[old_index]
                                        temp['Index'] = new_index + 1
                                        ins[old_index] = ins[new_index]
                                        ins[old_index]['Index'] = old_index + 1
                                        a.target.value = 0
                                        this.$set(ins, new_index, temp)
                                    },
                                },
                                props: {
                                        'min': 0
                                },
                                style: {
                                    'width': '80px'
                                }
                            }
                        )}
                    },
                    {title: '序号', key: 'Index'},
                    {title: '名称', key: 'Name'},
                    {title: '注释', key: 'Comment'}
                ],
                setterTableColumns2: [
                    {title: '序号', key: 'Index'},
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
                                        let outs = this.outToIns.outs
                                        outs.splice(params.index, 1)
                                        for (let index = 0; index <outs.length; index++) {
                                            outs[index]['Index'] = index + 1
                                        }
                                        this.outToIns.outs = outs
                                    }
                                }
                            })
                    }}
                ],
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
                    // eslint-disable-next-line no-console
                    console.log(this.outToIns)
                    let oi = this.outToIns
                    let inStr = ''
                    let outStr = ''
                    for (let index = 0; index < oi.ins.length; index++) {
                        let strComment = `//${oi.ins[index]['Comment']}\n`
                        let nameIn = oi.ins[index]['Name']
                        let nameOut = oi.outs[index]['Name']
                        inStr += strComment
                        outStr += strComment
                        inStr += `in.set${nameIn[0].toUpperCase()}${nameIn.slice(1)}(out.get${nameOut[0].toUpperCase()}${nameOut.slice(1)}())\n`
                        outStr += `out.set${nameOut[0].toUpperCase()}${nameOut.slice(1)}(in.get${nameIn[0].toUpperCase()}${nameIn.slice(1)}())\n`
                    }
                    this.nodes0str = inStr
                    this.nodes2str = outStr
                    return;
                }
                this.post()
            },
            post() {
                this.$post("/setter", this.message, result=>{
                    let data = result.data
                    if (data && data['In']) {
                        for (let index = 0; index < data['In'].length; index++) {
                            data['In'][index]['Index'] = index + 1
                        }
                        for (let index = 0; index < data['Out'].length; index++) {
                            data['Out'][index]['Index'] = index + 1
                        }
                        this.outToIns = {
                            ins: data['In'],
                            insChecked: 0,
                            outs: data['Out'],
                            outsChecked: 0
                        }
                        // eslint-disable-next-line no-console
                        console.log(this.outToIns)
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