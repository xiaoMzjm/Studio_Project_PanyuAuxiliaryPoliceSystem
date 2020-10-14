/**
 * 公共方法
 */
// import axios from 'axios'

// 找到根单位
const findRootUnit = function(code, list) {
  if (!code) return
  let rootUnit = {}
  list = list || []
  list.forEach(unit => {
    if (unit.code == code) rootUnit = unit
    if (unit.children && findRootUnit(code, unit.children).code) {
      rootUnit = unit
    }
  })
  return rootUnit
}

export { findRootUnit }