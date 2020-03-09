export default {
    rules: {
        title: [
            {required: true, message: 'Please enter title', trigger: 'blur'},
            {max: 50, message: 'Length of the title should be at most 50 characters', trigger: 'blur'},
        ],
        category: [
            {required: true, message: 'Please select one category', trigger: 'blur'},
        ],
        tag: [
            {required: true, message: 'Please select at lease one tag', trigger: 'blur'}
        ],
        content: [
            {required: true, message: 'Please enter content', trigger: 'blur'}
        ],
        original: [
            {required: true, message: 'Please select', trigger: 'blur'}
        ],
        author: [
            {required: true, message: 'Please enter author of the article', trigger: 'blur'}
        ],
        password: [
            {required: false},
            {
                pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/,
                message: 'Password has to be 8 - 16 characters long, and it at least contains one uppercase letter, one lowercase letter and one number.',
                trigger: 'blur'
            }
        ],
        nickname: [
            {required: true, message: 'Nickname cannot be empty.', trigger: 'blur'},
        ],
        email: [
            {required: true, message: 'Email cannot be empty.', trigger: 'blur'},
            {
                // eslint-disable-next-line no-useless-escape
                pattern: /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/,
                message: 'Please enter a valid email address.',
                trigger: 'blur'
            }
        ],
        bio: [
            {required: true, message: 'Please enter your bio', trigger: 'blur'}
        ],
    },
}