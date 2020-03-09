<template>
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <span id="header">Version Info</span>
        </div>
        <div >
            <el-divider content-position="left">Current Version</el-divider>
            <div class="info">{{generalStats.version}}</div>
            <el-divider content-position="left">Frameworks</el-divider>
            <div class="info backend">Backend: <span class="framework">Spring Boot</span> + <span class="framework">Spring Data JPA</span> + <span class="framework">Shiro</span></div>
            <div class="info">Frontend: <span class="framework">Vue.js</span> + <span class="framework">Element UI</span></div>
            <el-divider content-position="left">Source Code</el-divider>
            <div class="info"><a href="https://github.com/Uncharted30">https://github.com/Uncharted30</a></div>
            <el-divider content-position="left">Contact Me</el-divider>
            <div class="info">Email: {{generalStats.email}}</div>
        </div>
    </el-card>
</template>

<script>
    export default {
        name: "VersionInfoCard",
        props: ['generalStats']
    }
</script>

<style scoped>
    #header {
        font-weight: 500;
    }

    .info {
        text-align: left;
        padding-left: 10%;
        font-size: 15px;
    }

    .backend {
        margin-bottom: 6px;
    }

    .framework {
        font-weight: 500;
    }

    .el-divider__text {
        color: #408EFF;
    }
</style>