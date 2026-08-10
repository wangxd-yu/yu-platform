const fabric = require('@umijs/fabric');

module.exports = {
  ...fabric.prettier,
  printWidth: 150, // 设置每行代码的最大字符数
};
