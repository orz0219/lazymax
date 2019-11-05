<template>
    <div>
        <el-row style="margin-bottom: 20px;">
            <el-tag style="font-size: 20pt;">快速开发小工具</el-tag>
        </el-row>
        <el-row :gutter="12">
            <el-col :span="8">
                <el-card shadow="never">
                    <el-tabs v-model="activeTabs" type="card">
                        <el-tab-pane label="当前表信息" name="table">
                            <el-form :model="tableMessage">
                                <el-form-item label="表名称:">
                                    {{tableMessage.name}}
                                </el-form-item>
                                <el-form-item label="详情：">
                                    <el-row>
                                        <el-col :span="8">123</el-col>
                                        <el-col :span="8">456</el-col>
                                    </el-row>
                                </el-form-item>
                            </el-form>
                        </el-tab-pane>
                    </el-tabs>
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="hover">
                    鼠标悬浮时显示
                </el-card>
            </el-col>
            <el-col :span="8">
                <el-card shadow="never">
                    从不显示
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        name: "TableToEntity",
        data() {
            return {
                activeTabs: "table",
                tableMessage: {
                  name: "goods"
                },
                form: {
                    name: 123
                }
            }
        }
    }
</script>

<style scoped>

</style>