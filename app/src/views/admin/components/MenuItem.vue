<template>
    <el-menu-item :index="index" class="sub-menu-item">
        <i :class="icon + ' icon'"></i>
        <span slot="title">{{title}}</span>
    </el-menu-item>
</template>

<script>
    export default {
        name: "MenuItem",
        props: ['index', 'icon', 'title']
    }
</script>

<style scoped>
    .icon {
        color: white;
        padding-bottom: 4px;
    }

    .sub-menu-item {
        /*background-color: #4b556e;*/
        width: 230px;
    }
</style>