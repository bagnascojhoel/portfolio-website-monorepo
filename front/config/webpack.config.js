const { merge } = require('webpack-merge');
const parts = require('./webpack.parts');
const { mode, analyze, env } = require('webpack-nano/argv');

const common = merge([
    { output: { path: parts.STATIC_FILES_PATH } },
    parts.page(),
    parts.loadSvg(),
    parts.svelte(mode),
    parts.extractCSS({ loaders: [parts.postcss()] }),
    parts.cleanDist(),
    parts.useWebpackBar(),
    parts.useDotenv(env),
    parts.useTsconfigPaths(),
    parts.copyAssets(),
]);

const development = merge([
    { entry: ['./src/main.ts', 'webpack-plugin-serve/client'] },
    { target: 'web' },
    parts.generateSourceMaps({ type: 'eval-source-map' }),
    parts.esbuild(),
    parts.devServer(),
]);

const production = merge([
    { entry: ['./src/main.ts'] },
    parts.typescript(),
    parts.optimize(),
    analyze ? parts.analyze() : {},
]);

const getConfig = (mode) => {
    switch (mode) {
        case 'production':
            return merge(common, production, { mode });
        case 'development':
            return merge(common, development, { mode });
        default:
            throw new Error(`Unknown mode, ${mode}`);
    }
};

module.exports = getConfig(mode);
