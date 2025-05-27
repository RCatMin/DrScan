const path = require('path');

module.exports = {
    entry: {
        viewer: './script/viewer.js'
    },
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist'),
    },
    resolve: {
        fallback: {
            "fs": false,
            "path": require.resolve("path-browserify")
        }
    },
    module: {
        rules: [
            {
                test: /\.wasm/,
                type: 'asset/resource',
            }
        ]
    },
    mode: "development"
};