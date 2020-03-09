<template>
    <el-card class="box-card">
        <el-button type="primary" icon="el-icon-back" @click="back" size="mini" id="back-button">Back</el-button>
        <br>
        <el-form label-position="right" label-width="70px" :model="blog" :rules="rules">
            <el-form-item label="Title:" prop="title">
                <el-input v-model="blog.title" @change="onChange"></el-input>
            </el-form-item>
            <el-row>
                <el-col :span="8">
                    <div class="grid-content">
                        <el-form-item label="Category:" prop="category" class="category-selector">
                            <category-selector @setCategoryId="setCategoryId" @onChange="onChange"
                                               ref="categorySelector"></category-selector>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="8">
                    <div class="grid-content bg-purple-light">
                        <el-form-item label="Tags:" prop="tag">
                            <tag-selector @setSelectedTags="setSelectedTags" @onChange="onChange"
                                          ref="tagSelector"></tag-selector>
                        </el-form-item>
                    </div>
                </el-col>
                <el-col :span="8"></el-col>
            </el-row>
            <el-form-item label="Original: " prop="original" @change="onChange">
                <el-radio v-model="blog.original" :label="true">Yes</el-radio>
                <el-radio v-model="blog.original" :label="false">No</el-radio>
            </el-form-item>
            <el-form-item label="Author" prop="author" style="width: 350px">
                <el-input v-model="blog.author" placeholder="Please enter the author of this blog" @change="onChange"></el-input>
            </el-form-item>
            <el-form-item :class="{hideIfOriginal:blog.original}" label="Article Url">
                <el-input v-model="blog.originalUrl"
                          placeholder="Please enter the original link or this blog" @change="onChange"></el-input>
            </el-form-item>
            <el-form-item label="Content:" prop="content">
                <markdown-editor ref="markdownEditor" @editor="editor" :content="blog.contentMd"
                                 height="600px"></markdown-editor>
            </el-form-item>
            <el-form-item>
                <image-uploader @setImgUrl="setHeaderImgUrl" :imgUrl="blog.headerImgUrl" @onChange="onChange"
                                ref="imageUploader"></image-uploader>
            </el-form-item>
            <el-form-item>
                <el-button type="warning" @click="saveAsDraft">Save as draft</el-button>
                <el-button type="primary" @click="postBlog">Post</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
    import MarkdownEditor from './MarkdownEditor'
    import ImageUploader from '@/components/ImageUploader'
    import Validator from "@/utils/Validator";
    import CategorySelector from '../CategorySelector'
    import TagSelector from '../TagSelector'

    export default {
        name: "index",
        created() {
            if (this.id != null) {
                this.$axios.get('api/blog/' + this.id).then((response) => {
                    if (response.data.code === 200) {
                        this.blog = response.data.data;
                        this.$refs.tagSelector.selectedTags = [];
                        this.blog.tags.forEach((item) => {
                            this.$refs.tagSelector.selectedTags.push(item.id);
                        });
                        this.$refs.categorySelector.categoryId = this.blog.category.id;
                        if (this.blog.headerImgUrl !== '') {
                            this.$refs.imageUploader.setImage(this.blog.headerImgUrl);
                        }
                    } else {
                        this.$message.error('Failed to fetch the post, please try again later! ' + response.data.message);
                    }
                }).catch((e) => {
                    this.$message.error('Failed to fetch the post, please try again later! ' + e);
                })
            }
        },
        data() {
            return {
                blog: {
                    title: "",
                    category: {
                        id: null,
                    },
                    tags: [],
                    contentMd: "",
                    content: "",
                    draft: false,
                    headerImgUrl: '',
                    original: true,
                    author: '',
                    originalUrl: ''
                },
                selectedTags: [],
                rules: Validator.rules,
                changed: false
            }
        },
        methods: {
            setHeaderImgUrl(headerImgUrl) {
                this.blog.headerImgUrl = headerImgUrl;
            },
            editor(val) {
                if (this.blog.contentMd !== val.md) {
                    this.changed = true;
                }
                this.blog.contentMd = val.md;
                this.blog.content = val.html;
            },
            submit() {
                this.handleTags();
                this.blog.content = this.$refs.markdownEditor.getLatestHtml();
                this.blog.contentMd = this.$refs.markdownEditor.getLatestMd();
                if (this.blog.title === null || this.blog.title.length === 0 || this.blog.title.length > 50) {
                    this.$message.error("Please enter a valid title");
                    return;
                }
                if (this.blog.category.id === null) {
                    this.$message.error("Please select one category");
                    return;
                }
                if (this.blog.tags === null || this.blog.tags.length === 0) {
                    this.$message.error("Please select at lease one tag");
                    return;
                }
                if (this.blog.content === null || this.blog.content.length === 0) {
                    this.$message.error("Please enter content");
                    return;
                }
                this.$axios({url: 'api/admin/blog', data: this.blog, method: this.type}).then(
                    (response) => {
                        if (response.data.code === 200) {
                            this.$message.success('Succeed!');
                            this.changed = false;
                            this.$router.replace('/admin/managePosts/postList');
                        } else {
                            this.$message.error('Failed to post a new blog! Please try again later!' + response.data.message);
                        }
                    }
                ).catch(e => {
                    this.$message.error('Failed to post a new Blog! ' + e);
                })
            },
            postBlog() {
                this.blog.draft = false;
                this.submit();
            },
            saveAsDraft() {
                this.blog.draft = true;
                this.submit()
            },
            handleTags() {
                console.log(this.$refs.tagSelector.selectedTags);
                this.blog.tags = [];
                this.$refs.tagSelector.selectedTags.forEach((item) => {
                    this.blog.tags.push({
                        id: item
                    })
                })
            },
            setCategoryId(id) {
                this.blog.category.id = id;
            },
            setSelectedTags(tags) {
                this.selectedTags = tags;
            },
            back() {
                this.$router.replace('/admin/managePosts/postList');
            },
            onChange() {
                this.changed = true
            }
        },
        components: {
            MarkdownEditor, ImageUploader, CategorySelector, TagSelector
        },
        props: ['id', 'type'],
    }
</script>

<style scoped>
    .el-select {
        width: 100%;
    }

    .CodeMirror-cursor {
        left: 10px !important;
    }

    .hideIfOriginal {
        display: none;
    }

    #back-button {
        margin-bottom: 25px;
    }
</style>

