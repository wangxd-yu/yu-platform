(self.webpackChunkant_design_pro=self.webpackChunkant_design_pro||[]).push([[531],{5966:function(ce,X,e){"use strict";var A=e(28991),J=e(81253),N=e(85893),Y=e(31649),he=["fieldProps","proFieldProps"],Q=["fieldProps","proFieldProps"],Z="text",W=function(O){var k=O.fieldProps,$=O.proFieldProps,b=(0,J.Z)(O,he);return(0,N.jsx)(Y.Z,(0,A.Z)({valueType:Z,fieldProps:k,filedConfig:{valueType:Z},proFieldProps:$},b))},G=function(O){var k=O.fieldProps,$=O.proFieldProps,b=(0,J.Z)(O,Q);return(0,N.jsx)(Y.Z,(0,A.Z)({valueType:"password",fieldProps:k,proFieldProps:$,filedConfig:{valueType:Z}},b))},U=W;U.Password=G,U.displayName="ProFormComponent",X.Z=U},34687:function(ce){ce.exports={container:"container___1sYa-",lang:"lang___l6cji",content:"content___2zk1-",top:"top___1C1Zi",header:"header___5xZ3f",logo:"logo___2hXsy",title:"title___1-xuF",desc:"desc___-njyT",main:"main___x4OjT",icon:"icon___rzGKO",other:"other___lLyaU",register:"register___11Twg",prefixIcon:"prefixIcon___23Xrx"}},3178:function(){},34193:function(ce,X,e){"use strict";e.r(X),e.d(X,{default:function(){return De}});var A=e(49111),J=e(19650),N=e(18106),Y=e(95562),he=e(34792),Q=e(48086),Z=e(39428),W=e(11849),G=e(3182),U=e(2824),ge=e(17462),O=e(76772),k=e(54618),$=e(89366),b=e(2603),c=e(80521),Ee=e(36108),je=e(48107),Te=e(39464),P=e(67294),Le=e(57663),Se=e(71577),Be=e(47673),Ie=e(4107),m=e(28991),se=e(55507),Ze=e(92137),xe=e(81253),ue=e(28481),Re=e(9715),Ce=e(55843),t=e(85893),p=e(74115),n=["rules","name","phoneName","fieldProps","captchaTextRender","captchaProps"],l=P.forwardRef(function(s,E){var x=Ce.Z.useFormInstance(),i=(0,P.useState)(s.countDown||60),D=(0,ue.Z)(i,2),z=D[0],L=D[1],K=(0,P.useState)(!1),re=(0,ue.Z)(K,2),H=re[0],B=re[1],I=(0,P.useState)(),j=(0,ue.Z)(I,2),R=j[0],V=j[1],Ae=s.rules,Ue=s.name,me=s.phoneName,ae=s.fieldProps,pe=s.captchaTextRender,M=pe===void 0?function(o,d){return o?"".concat(d," \u79D2\u540E\u91CD\u65B0\u83B7\u53D6"):"\u83B7\u53D6\u9A8C\u8BC1\u7801"}:pe,v=s.captchaProps,h=(0,xe.Z)(s,n),C=function(){var o=(0,Ze.Z)((0,se.Z)().mark(function d(F){return(0,se.Z)().wrap(function(y){for(;;)switch(y.prev=y.next){case 0:return y.prev=0,V(!0),y.next=4,h.onGetCaptcha(F);case 4:V(!1),B(!0),y.next=13;break;case 8:y.prev=8,y.t0=y.catch(0),B(!1),V(!1),console.log(y.t0);case 13:case"end":return y.stop()}},d,null,[[0,8]])}));return function(F){return o.apply(this,arguments)}}();return(0,P.useImperativeHandle)(E,function(){return{startTiming:function(){return B(!0)},endTiming:function(){return B(!1)}}}),(0,P.useEffect)(function(){var o=0,d=s.countDown;return H&&(o=window.setInterval(function(){L(function(F){return F<=1?(B(!1),clearInterval(o),d||60):F-1})},1e3)),function(){return clearInterval(o)}},[H]),(0,t.jsxs)("div",{style:(0,m.Z)((0,m.Z)({},ae==null?void 0:ae.style),{},{display:"flex",alignItems:"center"}),ref:E,children:[(0,t.jsx)(Ie.Z,(0,m.Z)((0,m.Z)({},ae),{},{style:{flex:1,transition:"width .3s",marginRight:8}})),(0,t.jsx)(Se.Z,(0,m.Z)((0,m.Z)({style:{display:"block"},disabled:H,loading:R},v),{},{onClick:function(){var o=(0,Ze.Z)((0,se.Z)().mark(function F(){var ie;return(0,se.Z)().wrap(function(g){for(;;)switch(g.prev=g.next){case 0:if(g.prev=0,!me){g.next=9;break}return g.next=4,x.validateFields([me].flat(1));case 4:return ie=x.getFieldValue([me].flat(1)),g.next=7,C(ie);case 7:g.next=11;break;case 9:return g.next=11,C("");case 11:g.next=16;break;case 13:g.prev=13,g.t0=g.catch(0),console.log(g.t0);case 16:case"end":return g.stop()}},F,null,[[0,13]])}));function d(){return o.apply(this,arguments)}return d}(),children:M(H,z)}))]})}),r=(0,p.G)(l),a=r,T=e(63185),S=e(9676),w=e(22270),q=e(31649),oe=["options","fieldProps","proFieldProps","valueEnum"],_=P.forwardRef(function(s,E){var x=s.options,i=s.fieldProps,D=s.proFieldProps,z=s.valueEnum,L=(0,xe.Z)(s,oe);return(0,t.jsx)(q.Z,(0,m.Z)({ref:E,valueType:"checkbox",valueEnum:(0,w.h)(z,void 0),fieldProps:(0,m.Z)({options:x},i),lightProps:(0,m.Z)({labelFormatter:function(){return(0,t.jsx)(q.Z,(0,m.Z)({ref:E,valueType:"checkbox",mode:"read",valueEnum:(0,w.h)(z,void 0),filedConfig:{customLightMode:!0},fieldProps:(0,m.Z)({options:x},i),proFieldProps:D},L))}},L.lightProps),proFieldProps:D},L))}),de=P.forwardRef(function(s,E){var x=s.fieldProps,i=s.children;return(0,t.jsx)(S.Z,(0,m.Z)((0,m.Z)({ref:E},x),{},{children:i}))}),ve=(0,p.G)(de,{valuePropName:"checked"}),le=ve;le.Group=_;var ye=le,Fe=e(952),ee=e(5966),te=e(50208),Ne=e(73727),Pe=e(29791),ne=e(36571),Oe=e(34687),u=e.n(Oe),fe=function(E){var x=E.content;return(0,t.jsx)(O.Z,{style:{marginBottom:24},message:x,type:"error",showIcon:!0})},Me=function(){var E=(0,P.useState)(!1),x=(0,U.Z)(E,2),i=x[0],D=x[1],z=(0,P.useState)({}),L=(0,U.Z)(z,2),K=L[0],re=L[1],H=(0,P.useState)("account"),B=(0,U.Z)(H,2),I=B[0],j=B[1],R=(0,te.tT)("@@initialState"),V=R.initialState,Ae=R.setInitialState,Ue=function(){var M=(0,G.Z)((0,Z.Z)().mark(function v(){var h,C;return(0,Z.Z)().wrap(function(d){for(;;)switch(d.prev=d.next){case 0:return d.next=2,V==null||(h=V.fetchUserInfo)===null||h===void 0?void 0:h.call(V);case 2:if(C=d.sent,!C){d.next=6;break}return d.next=6,Ae(function(F){return(0,W.Z)((0,W.Z)({},F),{},{currentUser:C})});case 6:case"end":return d.stop()}},v)}));return function(){return M.apply(this,arguments)}}(),me=function(){var M=(0,G.Z)((0,Z.Z)().mark(function v(h){var C,o,d,F,ie,y;return(0,Z.Z)().wrap(function(f){for(;;)switch(f.prev=f.next){case 0:return D(!0),f.prev=1,f.next=4,(0,ne.x4)((0,W.Z)((0,W.Z)({},h),{},{type:I}));case 4:if(C=f.sent,console.log(C),!C.token){f.next=18;break}return localStorage.setItem("auth_token",JSON.stringify(C)),o="\u767B\u5F55\u6210\u529F\uFF01",Q.ZP.success(o),f.next=12,Ue();case 12:if(te.m8){f.next=14;break}return f.abrupt("return");case 14:return d=te.m8.location.query,F=d,ie=F.redirect,te.m8.push(ie||"/"),f.abrupt("return");case 18:re(C),f.next=26;break;case 21:f.prev=21,f.t0=f.catch(1),console.log(f.t0),y="\u767B\u5F55\u5931\u8D25\uFF0C\u8BF7\u91CD\u8BD5\uFF01",Q.ZP.error(y);case 26:D(!1);case 27:case"end":return f.stop()}},v,null,[[1,21]])}));return function(h){return M.apply(this,arguments)}}(),ae=K.status,pe=K.type;return(0,t.jsxs)("div",{className:u().container,children:[(0,t.jsxs)("div",{className:u().content,children:[(0,t.jsxs)("div",{className:u().top,children:[(0,t.jsx)("div",{className:u().header,children:(0,t.jsxs)(Ne.rU,{to:"/",children:[(0,t.jsx)("img",{alt:"logo",className:u().logo,src:"".concat("/yu-platform/","logo.svg")}),(0,t.jsx)("span",{className:u().title,children:"Ant Design"})]})}),(0,t.jsx)("div",{className:u().desc,children:"Ant Design \u662F\u897F\u6E56\u533A\u6700\u5177\u5F71\u54CD\u529B\u7684 Web \u8BBE\u8BA1\u89C4\u8303"})]}),(0,t.jsxs)("div",{className:u().main,children:[(0,t.jsxs)(Fe.ZP,{initialValues:{autoLogin:!0},submitter:{searchConfig:{submitText:"\u767B\u5F55"},render:function(v,h){return h.pop()},submitButtonProps:{loading:i,size:"large",style:{width:"100%"}}},onFinish:function(){var M=(0,G.Z)((0,Z.Z)().mark(function v(h){return(0,Z.Z)().wrap(function(o){for(;;)switch(o.prev=o.next){case 0:me(h);case 1:case"end":return o.stop()}},v)}));return function(v){return M.apply(this,arguments)}}(),children:[(0,t.jsx)(Y.Z,{activeKey:I,onChange:j,items:[{label:"\u8D26\u6237\u5BC6\u7801\u767B\u5F55",key:"account"},{label:"\u624B\u673A\u53F7\u767B\u5F55",key:"mobile",disabled:!1}]}),ae==="error"&&pe==="account"&&(0,t.jsx)(fe,{content:"\u9519\u8BEF\u7684\u7528\u6237\u540D\u548C\u5BC6\u7801\uFF08admin/ant.design)"}),I==="account"&&(0,t.jsxs)(t.Fragment,{children:[(0,t.jsx)(ee.Z,{name:"tenantId",fieldProps:{size:"large",prefix:(0,t.jsx)(k.Z,{className:u().prefixIcon})},placeholder:"\u79DF\u6237\u7F16\u53F7: 1001 or 1002",rules:[{required:!0,message:"\u79DF\u6237\u7F16\u53F7\u662F\u5FC5\u586B\u9879\uFF01"}]}),(0,t.jsx)(ee.Z,{name:"username",fieldProps:{size:"large",prefix:(0,t.jsx)($.Z,{className:u().prefixIcon})},placeholder:"\u7528\u6237\u540D: admin or user",rules:[{required:!0,message:"\u7528\u6237\u540D\u662F\u5FC5\u586B\u9879\uFF01"}]}),(0,t.jsx)(ee.Z.Password,{name:"password",fieldProps:{size:"large",prefix:(0,t.jsx)(b.Z,{className:u().prefixIcon})},placeholder:"\u5BC6\u7801: ant.design",rules:[{required:!0,message:"\u5BC6\u7801\u662F\u5FC5\u586B\u9879\uFF01"}]})]}),ae==="error"&&pe==="mobile"&&(0,t.jsx)(fe,{content:"\u9A8C\u8BC1\u7801\u9519\u8BEF"}),I==="mobile"&&(0,t.jsxs)(t.Fragment,{children:[(0,t.jsx)(ee.Z,{fieldProps:{size:"large",prefix:(0,t.jsx)(c.Z,{className:u().prefixIcon})},name:"mobile",placeholder:"\u8BF7\u8F93\u5165\u624B\u673A\u53F7\uFF01",rules:[{required:!0,message:"\u624B\u673A\u53F7\u662F\u5FC5\u586B\u9879\uFF01"},{pattern:/^1\d{10}$/,message:"\u4E0D\u5408\u6CD5\u7684\u624B\u673A\u53F7\uFF01"}]}),(0,t.jsx)(a,{fieldProps:{size:"large",prefix:(0,t.jsx)(b.Z,{className:u().prefixIcon})},captchaProps:{size:"large"},placeholder:"\u8BF7\u8F93\u5165\u9A8C\u8BC1\u7801\uFF01",captchaTextRender:function(v,h){return v?"".concat(h," ","\u79D2\u540E\u91CD\u65B0\u83B7\u53D6"):"\u83B7\u53D6\u9A8C\u8BC1\u7801"},name:"captcha",rules:[{required:!0,message:"\u9A8C\u8BC1\u7801\u662F\u5FC5\u586B\u9879\uFF01"}],onGetCaptcha:function(){var M=(0,G.Z)((0,Z.Z)().mark(function v(h){return(0,Z.Z)().wrap(function(o){for(;;)switch(o.prev=o.next){case 0:Q.ZP.success("\u83B7\u53D6\u9A8C\u8BC1\u7801\u6210\u529F\uFF01\u9A8C\u8BC1\u7801\u4E3A\uFF1A1234");case 1:case"end":return o.stop()}},v)}));return function(v){return M.apply(this,arguments)}}()})]}),(0,t.jsxs)("div",{style:{marginBottom:24},children:[(0,t.jsx)(ye,{noStyle:!0,name:"autoLogin",children:"\u81EA\u52A8\u767B\u5F55"}),(0,t.jsx)("a",{style:{float:"right"},children:"\u5FD8\u8BB0\u5BC6\u7801 ?"})]})]}),(0,t.jsxs)(J.Z,{className:u().other,children:["\u5176\u4ED6\u767B\u5F55\u65B9\u5F0F :",(0,t.jsx)(Ee.Z,{className:u().icon}),(0,t.jsx)(je.Z,{className:u().icon}),(0,t.jsx)(Te.Z,{className:u().icon})]})]})]}),(0,t.jsx)(Pe.Z,{})]})},De=Me},76772:function(ce,X,e){"use strict";e.d(X,{Z:function(){return t}});var A=e(22122),J=e(28481),N=e(96156),Y=e(38819),he=e(15873),Q=e(43061),Z=e(73218),W=e(54549),G=e(68855),U=e(57119),ge=e(40847),O=e(68628),k=e(94184),$=e.n(k),b=e(63441),c=e(67294),Ee=e(53124),je=e(5467),Te=e(96159),P=e(6610),Le=e(5991),Se=e(10379),Be=e(44144),Ie=function(p){(0,Se.Z)(l,p);var n=(0,Be.Z)(l);function l(){var r;return(0,P.Z)(this,l),r=n.apply(this,arguments),r.state={error:void 0,info:{componentStack:""}},r}return(0,Le.Z)(l,[{key:"componentDidCatch",value:function(a,T){this.setState({error:a,info:T})}},{key:"render",value:function(){var a=this.props,T=a.message,S=a.description,w=a.children,q=this.state,oe=q.error,_=q.info,de=_&&_.componentStack?_.componentStack:null,ve=typeof T=="undefined"?(oe||"").toString():T,le=typeof S=="undefined"?de:S;return oe?c.createElement(t,{type:"error",message:ve,description:c.createElement("pre",null,le)}):w}}]),l}(c.Component),m=Ie,se=function(p,n){var l={};for(var r in p)Object.prototype.hasOwnProperty.call(p,r)&&n.indexOf(r)<0&&(l[r]=p[r]);if(p!=null&&typeof Object.getOwnPropertySymbols=="function")for(var a=0,r=Object.getOwnPropertySymbols(p);a<r.length;a++)n.indexOf(r[a])<0&&Object.prototype.propertyIsEnumerable.call(p,r[a])&&(l[r[a]]=p[r[a]]);return l},Ze={success:Y.Z,info:ge.Z,error:Q.Z,warning:G.Z},xe={success:he.Z,info:O.Z,error:Z.Z,warning:U.Z},ue=function(n){var l=n.description,r=n.icon,a=n.prefixCls,T=n.type,S=(l?xe:Ze)[T]||null;return r?(0,Te.wm)(r,c.createElement("span",{className:"".concat(a,"-icon")},r),function(){return{className:$()("".concat(a,"-icon"),(0,N.Z)({},r.props.className,r.props.className))}}):c.createElement(S,{className:"".concat(a,"-icon")})},Re=function(n){var l=n.isClosable,r=n.closeText,a=n.prefixCls,T=n.closeIcon,S=n.handleClose;return l?c.createElement("button",{type:"button",onClick:S,className:"".concat(a,"-close-icon"),tabIndex:0},r?c.createElement("span",{className:"".concat(a,"-close-text")},r):T):null},Ce=function(n){var l,r=n.description,a=n.prefixCls,T=n.message,S=n.banner,w=n.className,q=w===void 0?"":w,oe=n.style,_=n.onMouseEnter,de=n.onMouseLeave,ve=n.onClick,le=n.afterClose,ye=n.showIcon,Fe=n.closable,ee=n.closeText,te=n.closeIcon,Ne=te===void 0?c.createElement(W.Z,null):te,Pe=n.action,ne=se(n,["description","prefixCls","message","banner","className","style","onMouseEnter","onMouseLeave","onClick","afterClose","showIcon","closable","closeText","closeIcon","action"]),Oe=c.useState(!1),u=(0,J.Z)(Oe,2),fe=u[0],Me=u[1],De=c.useRef(),s=c.useContext(Ee.E_),E=s.getPrefixCls,x=s.direction,i=E("alert",a),D=function(j){var R;Me(!0),(R=ne.onClose)===null||R===void 0||R.call(ne,j)},z=function(){var j=ne.type;return j!==void 0?j:S?"warning":"info"},L=ee?!0:Fe,K=z(),re=S&&ye===void 0?!0:ye,H=$()(i,"".concat(i,"-").concat(K),(l={},(0,N.Z)(l,"".concat(i,"-with-description"),!!r),(0,N.Z)(l,"".concat(i,"-no-icon"),!re),(0,N.Z)(l,"".concat(i,"-banner"),!!S),(0,N.Z)(l,"".concat(i,"-rtl"),x==="rtl"),l),q),B=(0,je.Z)(ne);return c.createElement(b.default,{visible:!fe,motionName:"".concat(i,"-motion"),motionAppear:!1,motionEnter:!1,onLeaveStart:function(j){return{maxHeight:j.offsetHeight}},onLeaveEnd:le},function(I){var j=I.className,R=I.style;return c.createElement("div",(0,A.Z)({ref:De,"data-show":!fe,className:$()(H,j),style:(0,A.Z)((0,A.Z)({},oe),R),onMouseEnter:_,onMouseLeave:de,onClick:ve,role:"alert"},B),re?c.createElement(ue,{description:r,icon:ne.icon,prefixCls:i,type:K}):null,c.createElement("div",{className:"".concat(i,"-content")},T?c.createElement("div",{className:"".concat(i,"-message")},T):null,r?c.createElement("div",{className:"".concat(i,"-description")},r):null),Pe?c.createElement("div",{className:"".concat(i,"-action")},Pe):null,c.createElement(Re,{isClosable:!!L,closeText:ee,prefixCls:i,closeIcon:Ne,handleClose:D}))})};Ce.ErrorBoundary=m;var t=Ce},17462:function(ce,X,e){"use strict";var A=e(38663),J=e.n(A),N=e(3178),Y=e.n(N)}}]);
