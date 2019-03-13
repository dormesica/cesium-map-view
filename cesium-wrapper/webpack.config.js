const path = require('path');
const webpack = require('webpack');
const CopyPlugin = require('copy-webpack-plugin');

const mode = process.env.NODE_ENV || 'production';

const config = {
    mode,
    entry: './src/index.js',
    output: {
        filename: 'index.js',
        path: path.resolve(__dirname, 'dist'),
    },
    plugins: [new CopyPlugin([{ from: path.resolve(__dirname, 'static') }])],
    module: {
        rules: [
            {
                test: /\.m?js$/,
                exclude: /(node_modules)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env'],
                    },
                },
            },
        ],
    },
    plugins: [
        new webpack.DefinePlugin({
            PRODUCTION: mode === 'production',
        }),
    ],
};

if (mode === 'development') {
    config.devtool = 'eval-source-map';
    config.watch = true;
    config.devServer = {
        contentBase: path.join(__dirname, 'dist'),
        port: 8080,
    };
}

module.exports = config;
