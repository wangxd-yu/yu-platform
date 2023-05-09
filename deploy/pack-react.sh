# #################### 前端打包脚本 #################### #
REACT_FOLDER="F:/Git_Project/yu-platform/yu-react/"

cd $REACT_FOLDER

rm -rf ${REACT_FOLDER}dist/*

yarn run build

