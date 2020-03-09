<template>
    <div class="content-index">
        <ArticleContent v-if="blog != null" :article="blog"></ArticleContent>
        <div id="directory-content" class="directory-content initial headroom--not-bottom headroom--not-top pinned">
            <div id="directory">
            </div>
        </div>

        <div id="respond-post-334" class="comment-container">
            <comments v-if="blog != null" :blog-id="blog.id"></comments>
        </div>
    </div>
</template>

<script>
    import ArticleContent from "@/views/site/Article/components/ArticleContent";
    import Comments from "@/views/site/Article/components/Comments";

    export default {
        name: "index",
        data() {
            return {
                blogId: this.$route.params.id,
                blog: null
            }
        },
        created() {
            this.getBlog();
        },
        mounted() {
            setTimeout(() => {
                this.init();
            }, 1000)
        },
        components: {
            ArticleContent, Comments
        },
        methods: {
            getBlog() {
                this.$axios.get('api/blog/' + this.blogId).then(response => {
                    if (response.data.code === 200) {
                        this.blog = response.data.data;
                    } else {
                        this.$message.error('Failed to get the blog post, please refresh and try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get the blog post, please refresh and try again. ' + e);
                })
            },
            init() {
                //generates navigation on the right of the page
                let postDirectoryBuild = function () {
                    let postChildren = function children(childNodes, reg) {
                        let result = [],
                            isReg = typeof reg === 'object',
                            isStr = typeof reg === 'string',
                            node, i, len;
                        for (i = 0, len = childNodes.length; i < len; i++) {
                            node = childNodes[i];
                            if ((node.nodeType === 1 || node.nodeType === 9) &&
                                (!reg ||
                                    isReg && reg.test(node.tagName.toLowerCase()) ||
                                    isStr && node.tagName.toLowerCase() === reg)) {
                                result.push(node);
                            }
                        }
                        return result;
                    };
                    let createPostDirectory = function (article, directory, isDirNum) {
                        let contentArr = [],
                            titleId = [],
                            levelArr, root, level,
                            currentList, list, li, link, i, len;
                        levelArr = (function (article, contentArr, titleId) {
                            let titleElem = postChildren(article.childNodes, /^h\d$/),
                                levelArr = [],
                                lastNum = 1,
                                lastRevNum = 1,
                                count = 0,
                                guid = 1,
                                id = 'directory' + (Math.random() + '').replace(/\D/, ''),
                                num,
                                elem;
                            while (titleElem.length) {
                                elem = titleElem.shift();
                                contentArr.push(elem.innerHTML);
                                num = +elem.tagName.match(/\d/)[0];
                                if (num > lastNum) {
                                    levelArr.push(1);
                                    lastRevNum += 1;
                                } else if (num === lastRevNum ||
                                    num > lastRevNum && num <= lastNum) {
                                    levelArr.push(0);
                                } else if (num < lastRevNum) {
                                    levelArr.push(num - lastRevNum);
                                    lastRevNum = num;
                                }
                                count += levelArr[levelArr.length - 1];
                                lastNum = num;
                                elem.id = elem.id || (id + guid++);
                                titleId.push(elem.id);
                            }
                            if (count !== 0 && levelArr[0] === 1) levelArr[0] = 0;
                            return levelArr;
                        })(article, contentArr, titleId);
                        currentList = root = document.createElement('ul');
                        let dirNum = [0];
                        for (i = 0, len = levelArr.length; i < len; i++) {
                            level = levelArr[i];
                            if (level === 1) {
                                list = document.createElement('ul');
                                if (!currentList.lastElementChild) {
                                    currentList.appendChild(document.createElement('li'));
                                }
                                currentList.lastElementChild.appendChild(list);
                                currentList = list;
                                dirNum.push(0);
                            } else if (level < 0) {
                                level *= 2;
                                while (level++) {
                                    if (level % 2) dirNum.pop();
                                    currentList = currentList.parentNode;
                                }
                            }
                            dirNum[dirNum.length - 1]++;
                            li = document.createElement('li');
                            link = document.createElement('a');
                            link.href = '#' + titleId[i];
                            link.innerHTML = !isDirNum ? contentArr[i] :
                                dirNum.join('.') + ' ' + contentArr[i];
                            li.appendChild(link);
                            currentList.appendChild(li);
                        }
                        directory.appendChild(root);
                    };
                    createPostDirectory(document.getElementById('post-content'), document.getElementById('directory'), true);
                };
                postDirectoryBuild();
            },

            floatMenu() {
                document.body.scrollTop = 0;
                document.documentElement.scrollTop = 0;
            }

        }
    }
</script>
<style scoped>
    @import "~@/assets/styles/style.min.css";
    @import "~@/assets/styles/xcode.min.css";

    .content-index {
        padding: 0;
        background-color: #fff;
    }
</style>
