const path = require('path');

module.exports = {
    entry: './script/imaging-record.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist'),
    },
    resolve: {
        fallback: {
            "fs" : false,
            "path" : require.resolve("path-browserify")
        }
    },
    mode: 'development',
    experiments: {
        asyncWebAssembly: true
    },
    module: {
        rules: [
            {
                test: /\.wasm$/,
                type: "webassembly/async",
            }
        ]
    }
};