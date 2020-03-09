<template>
    <el-card class="box-card">
        <div id="welcome-card-box">
            <el-row>
                <el-col :span="3">
                    <div id="avatar">
                        <pan-thumb v-if="avatarUrl !== null" :image="avatarUrl" width="80px" height="80px"></pan-thumb>
                    </div>
                </el-col>
                <el-col :span="9">
                    <div class="grid-content greetings">
                        <div id="welcome">{{welcome}}</div>
                        <div id="last-login">Last login time: {{generalStats.lastLogin}}</div>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="grid-content">
                        <el-row>
                            <el-col :span="8">
                                <div class="grid-content">
                                    <div class="title">Today's Visits</div>
                                    <br>
                                    <div class="count">{{generalStats.todayVisits}}</div>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="grid-content">
                                    <div class="title">Total Posts</div>
                                    <br>
                                    <div class="count">{{generalStats.posts}}</div>
                                </div>
                            </el-col>
                            <el-col :span="8">
                                <div class="grid-content">
                                    <div class="title">Total Comments</div>
                                    <br>
                                    <div class="count">{{generalStats.comments}}</div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-col>
            </el-row>
        </div>
    </el-card>
</template>

<script>
    import PanThumb from '@/components/PanThumb'

    export default {
        name: "WelcomeCard",
        created() {
            setTimeout(() => {
                this.avatarUrl = 'api' + this.generalStats.avatarUrl;
            }, 500);
        },
        updated() {
            let hour = new Date().getHours();
            if (hour <= 7) {
                this.welcome = 'It\'s late, ' + this.generalStats.nickname + '. Go have some rest.'
            } else if (hour >= 5 && hour <= 9) {
                this.welcome = 'Good morning, ' + this.generalStats.nickname + '. Do you feel like posting a new blog today?'
            } else if (hour > 9 && hour <= 12) {
                this.welcome = 'Hi, ' + this.generalStats.nickname + '. How are you doing today?'
            } else if (hour > 12 && hour <= 18) {
                this.welcome = 'Good afternoon, ' + this.generalStats.nickname + '. A little tired? Have some rest.'
            } else {
                this.welcome = 'Good night, ' + this.generalStats.nickname + '. Play a game and rest yourself a little bit.'
            }
        },
        data() {
            return {
                welcome: "",
                avatarUrl: null
            }
        },
        components: {
            PanThumb
        },
        props: ['generalStats']
    }
</script>

<style scoped>
    .text {
        font-size: 14px;
    }

    .item {
        padding: 18px 0;
    }

    .box-card {
        width: 100%;
        height: 200px;
        align-items: center;
    }

    #welcome-card-box {
        margin: 5px;
        border-style: solid;
        border-color: rgba(184, 189, 194, 0.5);
        border-width: 0.3px;
        display: flex;
        align-items: center;
        justify-content: center;
        height: 150px;
    }

    #welcome, #last-login {
        width: 100%;
        text-align: left;
    }

    .el-row {
        width: 100%;
        display: flex;
        align-items: center;
    }

    #welcome {
        margin-bottom: 10px;
    }

    .el-card {
        font-size: 17px;
        font-family: "Source Sans Pro", serif;
    }

    .title {
        font-size: 18px;
        font-weight: 600;
        color: #545C64;
        text-align: center;
    }

    .count {
        font-size: 18px;
        color: #409EFF;
        font-weight: 600;
        text-align: center;
    }

    #avatar {
        display: flex;
        justify-content: center;
    }
</style>