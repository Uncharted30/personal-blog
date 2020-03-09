<template>
    <div id="comments" class="clearfix">
        <span class="response">Comment</span>
        <form id="comment-form" class="comment-form" role="form">
            <label>
                <input type="text" v-model="form.nickname" maxlength="12" class="form-control input-control clearfix"
                       placeholder="Nickname (*)" value="" required="">
            </label>
            <label>
                <input type="email" v-model="form.email" class="form-control input-control clearfix"
                       placeholder="Email (*)" value="" required="">
            </label>
            <label>
                <textarea v-model="form.content" class="form-control" :placeholder="contentPlaceholder"
                          required=""></textarea>
            </label>
            <button type="button" class="submit" @click="submit">Comment</button>
        </form>
        <ol v-if="comments != null && comments.length > 0" class="comment-list">
            <li v-for="comment in comments" :id="'li-comment-' + comment.id" :key="comment.id"
                class="comment-body comment-parent comment-even">
                <div :id="'comment-' + comment.id">
                    <div class="comment-view" onclick="">
                        <div class="comment-header">
                            <img class="avatar" src="@/assets/images/user.png" width="80" height="80">
                            <span class="comment-author">
                              <a target="_blank" rel="external nofollow">{{comment.nickname}}</a>
                              <span>{{comment.device}}</span>
                            </span>
                        </div>
                        <div class="comment-content">
                            <span class="comment-author-at"></span>
                            <p>{{comment.content}}</p>
                            <p></p>
                        </div>
                        <div class="comment-meta">
                            <time class="comment-time">{{comment.createTime}}</time>
                            <span class="comment-reply">
                                <a href="#comments" rel="nofollow"
                                   @click="reply(comment.id, comment.nickname)">Reply</a>
                            </span>
                        </div>
                    </div>
                </div>
                <div v-if="comment.subComments !== null && comment.subComments.length > 0" class="comment-children">
                    <ol class="comment-list">
                        <li v-for="child in comment.subComments" :id="'li-comment-' + child.id" :key="child.id"
                            class="comment-body comment-child comment-level-odd comment-odd">
                            <div :id="'comment-' + child.id">
                                <div class="comment-view" onclick="">
                                    <div class="comment-header">
                                        <img class="avatar" src="@/assets/images/user.png" width="80" height="80">
                                        <span class="comment-author">
                                            <a rel="external nofollow">{{child.nickname}}</a>
                                            <span>{{child.device}}</span>
                                        </span>
                                    </div>
                                    <div class="comment-content">
                                        <span class="comment-author-at">
                                            <a :href="'#comment-' + comment.id">@{{comment.nickname}}</a>
                                        </span>
                                        <p>{{child.content}}</p>
                                        <p></p>
                                    </div>
                                    <div class="comment-meta">
                                        <time class="comment-time">{{child.createTime}}</time>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ol>
                </div>
            </li>
        </ol>
    </div>
</template>

<script>
    export default {
        name: "NewComment",
        data() {
            return {
                form: {
                    nickname: '',
                    email: '',
                    content: '',
                    blog: {
                        id: this.blogId
                    },
                    parentComment: null
                },
                comments: null,
                contentPlaceholder: 'Your comment here. Be cool.'
            }
        },
        mounted() {
          this.getBlogComments()
        },
        methods: {
            submit() {
                this.$axios.post('api/comment', this.form).then(response => {
                    if (response.data.code === 200) {
                        this.$message.success('Succeed');
                        this.clearForm();
                        this.getBlogComments();
                    } else {
                        this.$message.error('Failed to post comment, please try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to post comment, please try again. ' + e);
                })
            },
            reply(id, nickname) {
                this.form.parentComment = {
                    id: id,
                };
                console.log(this.form.parentComment);
                this.contentPlaceholder = "@" + nickname;
            },
            getBlogComments() {
                this.$axios.get('api/comment/blog/' + this.blogId).then(response => {
                    if (response.data.code === 200) {
                        this.comments = response.data.data;
                    } else {
                        this.$message.error('Failed to get comments, please refresh the page and try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get comments, please refresh the page and try again. ' + e);
                });
            },
            clearForm() {
                this.form.nickname = '';
                this.form.email = '';
                this.form.content = '';
                this.form.parentComment = null;
                this.contentPlaceholder = 'Your comment here. Be cool.';
            },
        },
        props: ['blogId']
    }
</script>

<style scoped>
    .comment-list {
        margin-bottom: 50px;
    }

</style>