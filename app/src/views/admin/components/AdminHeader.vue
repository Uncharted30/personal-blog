<template>
    <div id="header">
        <el-menu mode="horizontal" >
            <el-row>
                <el-col :span="1">
                    <div class="grid-content">
                        <el-menu-item id="menu-icon" index="1">
                            <i class="el-icon-s-unfold icon" v-if="isCollapsed" @click="collapseMenu"></i>
                            <i class="el-icon-s-fold icon" v-else @click="collapseMenu"></i>
                        </el-menu-item>
                    </div>
                </el-col>
                <el-col :span="20">
                    <div class="grid-content">
                        <el-menu-item index="2" id="currentPath">
                            {{currentRoute}}
                        </el-menu-item>
                    </div>
                </el-col>
                <el-col :span="3">
                    <div class="grid-content">
                        <el-menu-item index="3" id="top-left-avatar">
                            <header-avatar></header-avatar>
                        </el-menu-item>
                    </div>
                </el-col>
            </el-row>
        </el-menu>
    </div>
</template>

<script>
    import headerAvatar from "@/views/admin/components/HeaderAvatar";

    export default {
        name: "admin-header",
        data() {
            return {
                isCollapsed: false,
            }
        },
        methods: {
            collapseMenu() {
                this.isCollapsed = !this.isCollapsed;
                this.$emit('toggleSideBar');
            },
            setCurrentRoute(currentRoute) {
                this.currentRoute = currentRoute;
            }
        },
        components: {
            headerAvatar
        },
        props: ['currentRoute']
    }
</script>

<style scoped>
    .el-menu {
        box-shadow: 0 0 2px 0 #4a5359;
    }

    .icon {
        padding-bottom: 3px;
    }

    #currentPath {
        padding-left: 10px;
        color: #505254;
        font-size: 16px;
    }

    #menu-icon {
        padding-right: 5px;
    }

    #top-left-avatar {
        display: flex;
        justify-content: flex-end;
    }

    #currentPath {
        display: flex;
        justify-content: flex-start;
    }

    .el-menu-item {
        cursor: auto;
    }

    .el-menu-item:focus, .el-menu-item:hover {
        background-color: #fff;
        text-decoration: none;
    }

    .is-active, .icon {
        color: #505254 !important;
    }

    #header:focus {
        outline: 0;
    }
</style>