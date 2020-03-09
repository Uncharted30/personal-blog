<template>
    <div>
        <div class="footer-social">
            <div class="footer-container clearfix">
                <div class="social-list">
                    <a class="social rss" target="blank" :href="item.url" v-for="item in links" :key="item.id">{{item.name}}</a>
                </div>
            </div>
        </div>
        <div class="footer-meta">
            <div class="footer-container">
                <div class="meta-item meta-copyright">
                    <div class="meta-copyright-info">
                        <a href="/" class="info-logo">
                            <img src="@/assets/images/logo.png" alt="</>">
                        </a>
                        <div class="info-text"><p id="chakhsu">I work with J<span
                                style="color: rgb(255, 120, 71);">&amp;</span><span
                                style="color: rgb(255, 94, 99);">4</span><span
                                style="color: rgb(255, 94, 99);">U</span><span
                                style="color: rgb(191, 60, 175);">+</span><span
                                style="color: rgb(226, 183, 47);">`</span>
                        </p>
                            <p>Style is <a href="https://github.com/chakhsu/pinghsu" target="_blank">Pinghsu</a> by <a
                                    href="https://www.linpx.com/" target="_blank">Chakhsu</a></p>
                            <p>© 2020 <a href="https://www.annanpan.com/">Annan</a></p></div>
                    </div>
                </div>
                <div class="meta-item meta-posts">
                    <h3 class="meta-title">RECENT POSTS</h3>
                    <div v-if="blogList != null">
                        <p v-for="item in blogList" :key="item.id">
                            <router-link :to="'/blog/' + item.id">{{item.title}}</router-link>
                        </p>
                    </div>
                </div>
                <div class="meta-item meta-comments">
                    <h3 class="meta-title">RECENT COMMENTS</h3>
                    <div v-if="blogList != null">
                        <p v-for="item in commentList" :key="item.id">
                            <router-link :to="'/blog/' + item.blog.id">{{item.nickname}} : {{item.content}}</router-link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SiteFooter",
        data() {
            return {
                blogList: null,
                commentList: null,
                links: null,
            }
        },
        created() {
            this.fetchData()
        },
        mounted() {
            this.init()
        },
        methods: {
            fetchData() {
                this.$axios.get('api/blogroll').then(response => {
                    if (response.data.code === 200) {
                        this.links = response.data.data;
                    } else {
                        this.$message.error('Failed to get links, please refresh the page and try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get links, please refresh the page and try again. ' + e);
                });
                this.$axios.get('api/blog/list/5/0').then(response => {
                    if (response.data.code === 200) {
                        this.blogList = response.data.data.content;
                    } else {
                        this.$message.error('Failed to get recent blog, please refresh the page and try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get recent blog, please refresh the page and try again. ' + e);
                });
                this.$axios.get('api/comment/list/5/0').then(response => {
                    if (response.data.code === 200) {
                        this.commentList = response.data.data.content;
                    } else {
                        this.$message.error('Failed to get recent comments, please refresh the page and try again. ' + response.data.message);
                    }
                }).catch(e => {
                    this.$message.error('Failed to get recent comments, please refresh the page and try again. ' + e);
                });
            },
            init() {
                let r = document.getElementById('chakhsu')
                if (r.loaded) {
                    return false;
                }

                function t() {
                    return b[Math.floor(Math.random() * b.length)]
                }

                function e() {
                    return String.fromCharCode(94 * Math.random() + 33)
                }

                function n(r) {
                    let n = document.createDocumentFragment()
                    for (let i = 0; r > i; i++) {
                        let l = document.createElement("span");
                        l.textContent = e(), l.style.color = t(), n.appendChild(l)
                    }
                    return n
                }

                function i() {
                    let t = o[c.skillI];
                    c.step ? c.step-- : (c.step = g, c.prefixP < l.length ? (c.prefixP >= 0 && (c.text += l[c.prefixP]), c.prefixP++) : "forward" === c.direction ? c.skillP < t.length ? (c.text += t[c.skillP], c.skillP++) : c.delay ? c.delay-- : (c.direction = "backward", c.delay = a) : c.skillP > 0 ? (c.text = c.text.slice(0, -1), c.skillP--) : (c.skillI = (c.skillI + 1) % o.length, c.direction = "forward")), r.textContent = c.text, r.appendChild(n(c.prefixP < l.length ? Math.min(s, s + c.prefixP) : Math.min(s, t.length - c.skillP))), setTimeout(i, d)
                }

                let l = "I work with ",
                    o = ["Java", "C++", "Spring", "passion & love"].map(function (r) {
                        return r + "."
                    }), a = 2, g = 1, s = 5, d = 75,
                    b = ["rgb(110,64,170)", "rgb(150,61,179)", "rgb(191,60,175)", "rgb(228,65,157)", "rgb(254,75,131)", "rgb(255,94,99)", "rgb(255,120,71)", "rgb(251,150,51)", "rgb(226,183,47)", "rgb(198,214,60)", "rgb(175,240,91)", "rgb(127,246,88)", "rgb(82,246,103)", "rgb(48,239,130)", "rgb(29,223,163)", "rgb(26,199,194)", "rgb(35,171,216)", "rgb(54,140,225)", "rgb(76,110,219)", "rgb(96,84,200)"],
                    c = {text: "", prefixP: -s, skillI: 0, skillP: 0, direction: "forward", delay: a, step: g};
                i();
                r.loaded = true;
            }
        }
    }
</script>

<style scoped>
</style>