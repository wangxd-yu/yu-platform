(self.webpackChunkant_design_pro=self.webpackChunkant_design_pro||[]).push([[915],{44140:function(J,C,e){"use strict";e.r(C);var Q=e(57663),A=e(71577),X=e(62350),y=e(24565),d=e(39428),h=e(3182),Y=e(77576),p=e(12028),w=e(20136),j=e(55241),k=e(49111),T=e(19650),q=e(94233),f=e(51890),ee=e(62999),K=e(54680),M=e(2824),o=e(67294),R=e(89366),W=e(49101),L=e(78009),Z=e(40759),v=e(72338),g=e(75159),F=e(49868),S=e(90102),b=e(45254),n=e(85893),V=function x(E){var P=[];return Array.isArray(E)&&(E==null||E.forEach(function(i){var r={};r.key=i.id,r.value=i.id,r.title=i.name,r.children=i.children?x(i.children):[],P.push(r)})),P},G=function(){var E=(0,o.useState)(!1),P=(0,M.Z)(E,2),i=P[0],r=P[1],$=(0,o.useState)(),I=(0,M.Z)($,2),D=I[0],O=I[1],z=(0,o.useState)(),U=(0,M.Z)(z,2),B=U[0],H=U[1],a=(0,o.useRef)(),c=(0,o.useRef)();(0,o.useEffect)(function(){S.et((0,b.Ui)("/dept/tree")).then(function(_){H(V(_.data))})},[]);var N=[{title:"\u6240\u5C5E\u90E8\u95E8",dataIndex:"deptId",hideInTable:!0,renderFormItem:function(){return(0,n.jsx)(K.Z,{style:{width:"100%"},listHeight:300,dropdownStyle:{maxHeight:400,overflow:"auto"},treeData:B,placeholder:"\u8BF7\u9009\u62E9",treeDefaultExpandAll:!0})}},{dataIndex:"avatar",title:"\u5934\u50CF",align:"center",valueType:"avatar",width:150,search:!1,render:function(s,t){return(0,n.jsx)(j.Z,{content:(0,n.jsx)(T.Z,{children:t.portraitUrl?(0,n.jsx)(f.C,{size:{xs:24,sm:32,md:40,lg:64,xl:80,xxl:100},src:"/".concat("/api","/").concat(t.portraitUrl)}):(0,n.jsx)(f.C,{size:{xs:24,sm:32,md:40,lg:64,xl:80,xxl:100},icon:(0,n.jsx)(R.Z,{})})}),trigger:"hover",children:(0,n.jsx)(T.Z,{children:t.portraitUrl?(0,n.jsx)(f.C,{src:"/".concat("/api","/").concat(t.portraitUrl)}):(0,n.jsx)(f.C,{icon:(0,n.jsx)(R.Z,{})})})})}},{title:"\u7528\u6237\u8D26\u53F7",tip:"\u7528\u6237\u8D26\u53F7\u662F\u552F\u4E00\u7684 key",dataIndex:"username"},{title:"\u7528\u6237\u6635\u79F0",dataIndex:"name"},{title:"\u6240\u5C5E\u90E8\u95E8",dataIndex:"deptFullName",search:!1},{title:"\u72B6\u6001",align:"center",dataIndex:"enabled",valueType:"select",valueEnum:{true:{text:"\u542F\u7528"},false:{text:"\u505C\u7528"}},render:function(s,t){return[(0,n.jsx)(p.Z,{checked:t.enabled,checkedChildren:"\u542F\u7528",unCheckedChildren:"\u505C\u7528",onChange:function(){var m;t.enabled?(0,v.B9)(t.id):(0,v.Dx)(t.id),(m=c.current)===null||m===void 0||m.reload()}},"enabled")]}},{title:"\u521B\u5EFA\u65F6\u95F4",align:"center",sorter:!0,search:!1,dataIndex:"createTime",valueType:"dateTime"},{title:"\u64CD\u4F5C",align:"center",dataIndex:"option",valueType:"option",render:function(s,t){return[(0,n.jsx)("a",{onClick:(0,h.Z)((0,d.Z)().mark(function u(){return(0,d.Z)().wrap(function(l){for(;;)switch(l.prev=l.next){case 0:return l.next=2,(0,v.PR)(t.id,O);case 2:r(!0);case 3:case"end":return l.stop()}},u)})),children:"\u7F16\u8F91"},"update"),(0,n.jsx)(y.Z,{title:"\u786E\u8BA4\u5220\u9664\u8BE5\u8BB0\u5F55\u5417?",okText:"\u662F",cancelText:"\u5426",onConfirm:(0,h.Z)((0,d.Z)().mark(function u(){return(0,d.Z)().wrap(function(l){for(;;)switch(l.prev=l.next){case 0:return l.next=2,g.fn(v.h8,t.id);case 2:c.current&&c.current.reload();case 3:case"end":return l.stop()}},u)})),children:(0,n.jsx)("a",{href:"#",children:"\u5220\u9664"},"delete")},"deleteConfirm")]}}];return(0,n.jsxs)(L.ZP,{header:{breadcrumb:{}},children:[(0,n.jsx)(Z.ZP,{headerTitle:"\u67E5\u8BE2\u8868\u683C",actionRef:c,rowKey:"id",search:{labelWidth:120},toolBarRender:function(){return[(0,n.jsxs)(A.Z,{type:"primary",onClick:function(){var t;a==null||(t=a.current)===null||t===void 0||t.resetFields(),O(void 0),r(!0)},children:[(0,n.jsx)(W.Z,{})," \u65B0\u5EFA"]},"primary")]},request:v.tM,columns:N}),i&&(0,n.jsx)(F.Z,{deptTree:B,width:"700px",title:D!=null&&D.id?"\u66F4\u65B0\u7528\u6237":"\u65B0\u5EFA\u7528\u6237",visible:i,onVisibleChange:function(s){if(s){var t;a==null||(t=a.current)===null||t===void 0||t.resetFields()}else r(!1),O(void 0)},formRef:a,initialValues:D||{enabled:!0},onFinish:(0,h.Z)((0,d.Z)().mark(function _(){var s;return(0,d.Z)().wrap(function(u){for(;;)switch(u.prev=u.next){case 0:r(!1),a==null||(s=a.current)===null||s===void 0||s.resetFields(),c.current&&c.current.reload();case 3:case"end":return u.stop()}},_)}))})]})};C.default=G}}]);
