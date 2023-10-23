const defaultTheme = require('tailwindcss/defaultTheme');

module.exports = {
    content: [
        './public/index.html',
        './public/global.css',
        './src/**/*.svelte',
    ],
    media: false,
    theme: {
        extend: {
            fontFamily: {
                mono: ['"Fira Code"', ...defaultTheme.fontFamily['mono']],
            },
            fontSize: {
                sm: '10px',
                base: ['16px', '24px'],
            },
            backgroundImage: {
                'geometric-pattern': `url("assets/images/background-pattern.svg")`,
            },
            colors: {
                primary: '#F97316',
                'on-primary': '#F8FAFC',
                'primary-variant': '#FB923C',
                background: '#F8FAFC',
                'on-background': '#B9BBBD',
            },
        },
    },
    variants: {
        extend: {},
    },
    plugins: [],
};
