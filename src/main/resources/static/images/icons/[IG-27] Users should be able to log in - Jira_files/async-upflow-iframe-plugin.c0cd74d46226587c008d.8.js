try{window.performance.mark("async-upflow-iframe-plugin.js:eval-start")}catch(e){}(window.webpackJsonp=window.webpackJsonp||[]).push([["async-upflow-iframe-plugin"],{"./node_modules/@atlassiansox/iframe-plugin/dist/es2019/index.js":function(e,t,n){"use strict";n.d(t,"b",(function(){return $})),n.d(t,"d",(function(){return v})),n.d(t,"a",(function(){return u})),n.d(t,"c",(function(){return N})),n.d(t,"e",(function(){return D}));var r=n("./node_modules/@babel/runtime/helpers/defineProperty.js"),i=n.n(r),s=n("./node_modules/eventemitter3/index.js"),o=n.n(s);let a,c,l,u,d;function p(e,t){return{type:e,payload:t}}!function(e){e.Child="child",e.Parent="parent"}(a||(a={})),function(e){e.Handshake="handshake",e.Message="message"}(c||(c={}));class h{constructor(){i()(this,"emitter",new o.a),i()(this,"queue",[])}on(e,t){return this.emitter.on(e,t),this}off(e,t){return this.emitter.off(e,t),this}send(e,...t){this.port&&this.isReady()?this.port.postMessage(function(e,t){return p(c.Message,{type:e,
payload:t})}(e,t[0])):this.queue.push({name:e,payload:t})}dispatchQueuedMessages(){for(;this.queue.length>0;){const{name:e,payload:t}=this.queue.shift();this.send(e,...t)}}}class f extends h{constructor(e){super(),i()(this,"handshakeReceived",!1),i()(this,"onFrameLoadedEvent",(()=>{this.handshakeReceived=!1;const e=new MessageChannel;this.port=e.port1,this.port.addEventListener("message",this.onMessage),this.port.start(),this.frame.contentWindow&&this.frame.contentWindow.postMessage(p(c.Handshake),"*",[e.port2])})),i()(this,"onMessage",(e=>{const t=e.data;if(t.type===c.Handshake)return this.handshakeReceived=!0,this.dispatchQueuedMessages(),void this.emitter.emit(c.Handshake);if(t.type!==c.Message);else{const{type:e,payload:n}=t.payload;this.emitter.emit(e,n)}})),this.frame=e}connect(){this.setupFrameEventListeners()}isReady(){return this.handshakeReceived&&Boolean(this.port)}close(){this.port&&this.port.close()}setupFrameEventListeners(){
this.frame.addEventListener("load",this.onFrameLoadedEvent)}}!function(e){e.Handshake="handshake",e.Ready="ready",e.Message="message",e.AnalyticEvent="analytic-event",e.ErrorBoundary="error-boundary"}(l||(l={})),function(e){e.Error="error",e.Message="message",e.AnalyticEvent="analytic-event",e.Handshake="handshake"}(u||(u={})),function(e){e.Child="child",e.Parent="parent"}(d||(d={}));const m="__spinner_wrapper";function y(){const e=function(){const e=document.createElement("style");return document.head.appendChild(e),e}();e.sheet instanceof CSSStyleSheet&&(e.sheet.insertRule(`\n        #${m} {\n            display: flex;\n            height: 96px;\n            width: 96px;\n        }`,e.sheet.cssRules.length),
e.sheet.insertRule("\n        .__spinner_svg {\n            fill: none;\n            stroke: rgb(66, 82, 110);\n            stroke-dasharray: 270.177px;\n            stroke-dashoffset: 216.142px;\n            stroke-linecap: round;\n            stroke-width: 10px;\n            transform-origin: center center;\n            animation: 0.86s cubic-bezier(0.4, 0.15, 0.6, 0.85) 0s infinite rotation;\n        }",e.sheet.cssRules.length),e.sheet.insertRule("@keyframes velocity {\n            0% {\n                transform: rotate(50deg);\n            }\n            100% {\n                transform: rotate(230deg);\n            }\n        }",e.sheet.cssRules.length),e.sheet.insertRule("@keyframes rotation {\n            100% {\n                transform: rotate(360deg);\n            }\n        }",e.sheet.cssRules.length))}const g={focusable:"false",height:96,size:96,width:96,viewBox:"0 0 96 96"},b={cx:"48",cy:"48",r:"43"},w=e=>t=>{e.setAttribute(t[0],String(t[1]))};function v(){
const e=document.createElement("span");e.id=m,e.setAttribute("size","96");const t=document.createElementNS("http://www.w3.org/2000/svg","svg");t.classList?t.classList.add("__spinner_svg"):t.setAttribute("class","__spinner_svg"),Object.entries(g).forEach(w(t));const n=document.createElementNS("http://www.w3.org/2000/svg","circle");return Object.entries(b).forEach(w(n)),t.appendChild(n),e.appendChild(t),y(),e}function E(e){const t=e.modalElement||function(e){const t=document.createElement("div");return t.style.position=e.withFullscreen?"absolute":"relative",t.style.top="0",t.style.left="0",t.style.padding="0",t.style.margin="0",t.style.height="100%",t.style.width="100%",t.style.background=e.withBlanket?"rgba(7, 71, 166, 0.3)":"transparent",t.style.display="flex",t.style.justifyContent="center",t.style.alignItems="center",t.style.zIndex=e.modalZIndex.toString(),t}(e);let n;return e.loaderElement?(n=e.loaderElement,t.appendChild(n)):e.withLoader&&(n=v(),t.appendChild(n)),{modal:t,spinner:n
}}function O(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function j(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?O(Object(n),!0).forEach((function(t){k(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):O(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function k(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}let _;!function(e){e.Initialisation="initialised",e.InitialisationError="initialisationFailed",e.Handshake="handshake",e.HandshakeTimeout="handshakeTimeout",e.Ready="ready",e.ReadyTimeout="readyTimeout"}(_||(_={}));class x{constructor(e){if(i()(this,"getAnalyticsEvent",(e=>{const t={}
;return this.isInitialisationEvent(e)&&this.startTimer(),this.isTimedEvent(e)&&Object.assign(t,this.getTimestamp(e)),{context:[{source:"growthKitIframePlugin"}],payload:{eventType:"operational",action:e,actionSubject:"spaParentClient",actionSubjectId:`${this.appName}SpaParentClient`,attributes:j({appName:this.appName},t)}}})),i()(this,"getTimestamp",(e=>{const t=e===_.Handshake?"timeToHandshake":"timeToReady";if(!this.initTime)throw new Error("No initialisation time (missing initialisation event)");return{[t]:Date.now()-this.initTime}})),i()(this,"isInitialisationEvent",(e=>-1!==[_.Initialisation].indexOf(e))),i()(this,"isTimedEvent",(e=>-1!==[_.Handshake,_.Ready].indexOf(e))),i()(this,"startTimer",(()=>{this.initTime=Date.now()})),!e)throw new Error("Please pass through an appName");this.appName=e}}class P{constructor(e,t){this.methods=new Map(t),this.client=e}start(){this.client.on("__RPC_INVOKE",(e=>{const{uuid:t,name:n,args:r}=e.data;this.onMethodCall(t,n,r)})),
this.client.send("__RPC_METHODS",{methods:Array.from(this.methods.keys())})}onMethodCall(e,t,n){const r=this.methods.get(t);if(r)try{r(...n).then((t=>{this.client.send("__RPC_RESULT",{data:{uuid:e,result:t}})})).catch((()=>{this.client.send("__RPC_ERROR",{data:{uuid:e,error:"METHOD_PROMISE_REJECTED"}})}))}catch(i){this.client.send("__RPC_ERROR",{data:{uuid:e,error:"METHOD_THREW_ERROR"}})}else this.client.send("__RPC_ERROR",{data:{uuid:e,error:"NOT_IMPLEMENTED"}})}}const I=["attributes"];function R(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function T(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?R(Object(n),!0).forEach((function(t){C(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):R(Object(n)).forEach((function(t){
Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function C(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function S(e,t){if(null==e)return{};var n,r,i=function(e,t){if(null==e)return{};var n,r,i={},s=Object.keys(e);for(r=0;r<s.length;r++)n=s[r],t.indexOf(n)>=0||(i[n]=e[n]);return i}(e,t);if(Object.getOwnPropertySymbols){var s=Object.getOwnPropertySymbols(e);for(r=0;r<s.length;r++)n=s[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(i[n]=e[n])}return i}const M=e=>((e,t)=>t.reduce(((t,n)=>(e.forEach((e=>{const r=n?n[e]:null;t.push(r||n[e])})),t)),[]).filter(Boolean))(["source"],e),L=e=>e[e.length-1],D=e=>{const{context:t,payload:n}=e;if(!n)return null;const r=M(t),i=L(r)||"unknown",s=(e=>e.reduce(((e,t)=>{let{attributes:n}=t,r=S(t,I);const i=T(T({},e),r);return n?T(T({},i),n):i}),{}))(t),{packageName:o,packageVersion:a}=L((e=>e.map((e=>({packageName:e.packageName,
packageVersion:e.packageVersion}))).filter((e=>e.packageName)))(t))||{},{eventType:c="ui",action:l,actionSubject:u,actionSubjectId:d,attributes:p,name:h}=n,f=T(T({packageName:o,packageVersion:a},s),p),m=n.tags||[];switch(m.push("growth"),c){case"ui":case"operational":case"track":return{eventType:c,source:i,actionSubject:u,action:l,actionSubjectId:d,attributes:f,tags:m.slice(0)};case"screen":return{eventType:c,name:h,attributes:f,source:i,tags:m.slice(0)}}return null},N=e=>t=>Promise.resolve(e).then((e=>{switch(t.eventType){case"ui":e.sendUIEvent(t);break;case"track":e.sendTrackEvent(t);break;case"operational":e.sendOperationalEvent(t);break;case"screen":e.sendScreenEvent(t)}}));class A{constructor(e=1e3,t){i()(this,"start",(()=>{this.timeoutID=window.setTimeout((()=>{this.onTimeout()}),this.delay)})),i()(this,"clear",(()=>{clearTimeout(this.timeoutID)})),this.delay=e,this.onTimeout=t}}function H(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){
var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function U(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?H(Object(n),!0).forEach((function(t){F(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):H(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function F(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}class ${constructor(e){if(i()(this,"iframeId","__iframe_plugin_"),i()(this,"events",new o.a),i()(this,"onHandshakeTimeout",(()=>{this.emitMonitoringEvent(_.HandshakeTimeout),this.events.emit(u.Error)})),i()(this,"onReadyTimeout",(()=>{this.emitMonitoringEvent(_.ReadyTimeout),this.events.emit(u.Error)})),i()(this,"analyticCallback",(e=>{this.events.emit(u.AnalyticEvent,e)
})),i()(this,"messageCallback",(e=>{this.events.emit(u.Message,e)})),i()(this,"readyCallback",(()=>{this.timeoutReadyEvent.clear(),this.hideLoader(),this.emitMonitoringEvent(_.Ready)})),i()(this,"handshakeCallback",(()=>{this.timeoutHandshakeEvent.clear(),this.emitMonitoringEvent(_.Handshake),this.events.emit(u.Handshake)})),i()(this,"emitMonitoringEvent",(e=>{this.events.emit(u.AnalyticEvent,this.iframeEvents.getAnalyticsEvent(e))})),!e)throw new Error("Please pass through valid options");this.options=e,this.timeoutHandshakeEvent=new A(e.handshakeEventTimeoutDelayMilliSeconds,this.onHandshakeTimeout),this.timeoutReadyEvent=new A(e.readyEventTimeoutDelayMilliSeconds,this.onReadyTimeout),this.iframeEvents=new x(this.options.appName)}on(e,t){return this.events.addListener(e,t),this}off(e,t){return this.events.removeListener(e,t),this}cleanup(){this.client&&this.client.close(),this.events.removeAllListeners();const e=this.selectorEl;e&&this.modal&&e.removeChild(this.modal),
this.timeoutHandshakeEvent.clear(),this.timeoutReadyEvent.clear(),delete this.selectorEl,delete this.modal,delete this.iframeEl}registerMessageListener(){this.client&&(this.client.on(l.Message,this.messageCallback),this.client.on(l.AnalyticEvent,this.analyticCallback),this.client.on(l.Handshake,this.handshakeCallback),this.client.on(l.Ready,this.readyCallback))}constructIframe(e){if(!e)throw new Error("No dom element found");this.selectorEl=e;const{modal:t,spinner:n}=E(U(U({},this.options),{},{modalZIndex:this.options.modalZIndex||200}));this.modal=t,this.spinner=n;const r=function(e,t,n){const[r,i]=e.split("#"),s=-1===r.indexOf("?")?"?":"&";return`${r}${s}${encodeURIComponent(t)}=${encodeURIComponent(n)}${i?`#${i}`:""}`}(this.options.src,"_c","2");this.iframeEl=function(e){const t=document.createElement("iframe");return t.src=e.src,t.height="100%",t.width="100%",t.id=e.id,t.style.display=e.withLoader?"none":"block",t.style.borderWidth="0",t.style.zIndex=e.zIndex.toString(),t}({
src:String(r),zIndex:this.options.iframeZIndex||200,id:this.iframeId,withLoader:Boolean(this.options.withLoader)||Boolean(this.options.loaderElement)}),this.modal.appendChild(this.iframeEl),e.appendChild(this.modal)}hideLoader(){this.spinner&&(this.spinner.style.display="none"),this.iframeEl&&this.modal&&(this.iframeEl.style.display="block",this.modal.style.background="transparent")}postMessage(e){this.client&&this.client.send(l.Message,e)}init({containerElement:e,rpcMethods:t}){try{this.emitMonitoringEvent(_.Initialisation),this.constructIframe(e),this.initialiseTransport(),this.initialiseRpc(t),this.registerMessageListener(),this.timeoutHandshakeEvent.start(),this.timeoutReadyEvent.start()}catch(n){throw this.emitMonitoringEvent(_.InitialisationError),this.events.emit(u.Error),n}}initialiseTransport(){if(!this.iframeEl)throw new Error("initialiseTransport should be called after iframe is created");this.client=new f(this.iframeEl),this.client.connect()}initialiseRpc(e=[]){
if(!this.client)throw new Error("initialiseRpc should be called after client is instantiated");this.rpc=new P(this.client,e),this.rpc.start()}}n("./node_modules/@babel/runtime/helpers/extends.js"),n("./node_modules/react/index.js");let z;!function(e){e[e.string=0]="string",e[e.object=1]="object"}(z||(z={}))},"./node_modules/eventemitter3/index.js":function(e,t,n){"use strict";var r=Object.prototype.hasOwnProperty,i="~";function s(){}function o(e,t,n){this.fn=e,this.context=t,this.once=n||!1}function a(e,t,n,r,s){if("function"!=typeof n)throw new TypeError("The listener must be a function");var a=new o(n,r||e,s),c=i?i+t:t;return e._events[c]?e._events[c].fn?e._events[c]=[e._events[c],a]:e._events[c].push(a):(e._events[c]=a,e._eventsCount++),e}function c(e,t){0==--e._eventsCount?e._events=new s:delete e._events[t]}function l(){this._events=new s,this._eventsCount=0}Object.create&&(s.prototype=Object.create(null),(new s).__proto__||(i=!1)),l.prototype.eventNames=function(){var e,t,n=[]
;if(0===this._eventsCount)return n;for(t in e=this._events)r.call(e,t)&&n.push(i?t.slice(1):t);return Object.getOwnPropertySymbols?n.concat(Object.getOwnPropertySymbols(e)):n},l.prototype.listeners=function(e){var t=i?i+e:e,n=this._events[t];if(!n)return[];if(n.fn)return[n.fn];for(var r=0,s=n.length,o=new Array(s);r<s;r++)o[r]=n[r].fn;return o},l.prototype.listenerCount=function(e){var t=i?i+e:e,n=this._events[t];return n?n.fn?1:n.length:0},l.prototype.emit=function(e,t,n,r,s,o){var a=i?i+e:e;if(!this._events[a])return!1;var c,l,u=this._events[a],d=arguments.length;if(u.fn){switch(u.once&&this.removeListener(e,u.fn,void 0,!0),d){case 1:return u.fn.call(u.context),!0;case 2:return u.fn.call(u.context,t),!0;case 3:return u.fn.call(u.context,t,n),!0;case 4:return u.fn.call(u.context,t,n,r),!0;case 5:return u.fn.call(u.context,t,n,r,s),!0;case 6:return u.fn.call(u.context,t,n,r,s,o),!0}for(l=1,c=new Array(d-1);l<d;l++)c[l-1]=arguments[l];u.fn.apply(u.context,c)}else{var p,h=u.length
;for(l=0;l<h;l++)switch(u[l].once&&this.removeListener(e,u[l].fn,void 0,!0),d){case 1:u[l].fn.call(u[l].context);break;case 2:u[l].fn.call(u[l].context,t);break;case 3:u[l].fn.call(u[l].context,t,n);break;case 4:u[l].fn.call(u[l].context,t,n,r);break;default:if(!c)for(p=1,c=new Array(d-1);p<d;p++)c[p-1]=arguments[p];u[l].fn.apply(u[l].context,c)}}return!0},l.prototype.on=function(e,t,n){return a(this,e,t,n,!1)},l.prototype.once=function(e,t,n){return a(this,e,t,n,!0)},l.prototype.removeListener=function(e,t,n,r){var s=i?i+e:e;if(!this._events[s])return this;if(!t)return c(this,s),this;var o=this._events[s];if(o.fn)o.fn!==t||r&&!o.once||n&&o.context!==n||c(this,s);else{for(var a=0,l=[],u=o.length;a<u;a++)(o[a].fn!==t||r&&!o[a].once||n&&o[a].context!==n)&&l.push(o[a]);l.length?this._events[s]=1===l.length?l[0]:l:c(this,s)}return this},l.prototype.removeAllListeners=function(e){var t;return e?(t=i?i+e:e,this._events[t]&&c(this,t)):(this._events=new s,this._eventsCount=0),this},
l.prototype.off=l.prototype.removeListener,l.prototype.addListener=l.prototype.on,l.prefixed=i,l.EventEmitter=l,e.exports=l},"./src/packages/common-legacy-do-not-add-anything-new/src/util/editions/get-product-edition/index.js":function(e,t,n){"use strict";n.d(t,"a",(function(){return i}));var r=n("./src/packages/platform/utils/types/src/application-key.js");const i=(e,t)=>e===r.d?t.appEditions.software:e===r.c?t.appEditions.serviceDesk:t.appEditions.core},"./src/packages/growth/up-flow/up-flow-iframe/src/main.js":function(t,n,r){"use strict"
;var i=r("./node_modules/react/index.js"),s=r("./node_modules/lodash/noop.js"),o=r.n(s),a=r("./node_modules/@atlassiansox/iframe-plugin/dist/es2019/index.js"),c=r("./node_modules/query-string/index.js"),l=r("./src/packages/common-legacy-do-not-add-anything-new/src/util/editions/get-product-edition/index.js"),u=r("./src/packages/platform/ui/jira-experience-tracker/src/ui/experience-start/main.js"),d=r("./src/packages/platform/ui/jira-experience-tracker/src/ui/experience-success/main.js"),p=r("./src/packages/platform/ui/jira-experience-tracker/src/ui/experience-fail/main.js"),h=r("./src/packages/platform/controllers/tenant-context/src/components/tenant-context/index.js"),f=r("./node_modules/react-dom/index.js"),m=r.n(f),y=r("./node_modules/lodash/pick.js"),g=r.n(y),b=r("./src/packages/platform/observability/product-analytics-web-client/src/async.js"),w=r("./src/packages/platform/utils/fetch/src/utils/requests.js"),v=r("./src/packages/platform/utils/types/src/edition.js")
;const E=()=>Date.now();function O(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function j(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}const k=e=>new a.b(function(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?O(Object(n),!0).forEach((function(t){j(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):O(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}({appName:"upFlowSpa",withFullscreen:!0,modalZIndex:3200,handshakeEventTimeoutDelayMilliSeconds:5e3,readyEventTimeoutDelayMilliSeconds:1e4},e));function _(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e)
;t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function x(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}const P=t=>{const{src:n,subRoute:r="",onClose:i,onOpen:s,onSuccess:o,onFail:c,currentEdition:l,targetEdition:u,baseUrl:d,product:p,doc:h=document,win:f=window}=t,y=((e,t)=>{if("engaged-upgrade"===e||"engaged-billing"===e)return{withBlanket:!1,withLoader:!1,modalElement:(e=>{const t=e.createElement("div");return t.style.position="fixed",t.style.top="0",t.style.left="0",t.style.padding="0",t.style.margin="0",t.style.height="100%",t.style.width="100%",t.style.background="transparent",t.style.display="none",t.style.justifyContent="center",t.style.alignItems="center",t.style.zIndex="3200",t})(t)};return{withBlanket:!0,withLoader:!0}})(r,h),O=k(function(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{}
;t%2?_(Object(n),!0).forEach((function(t){x(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):_(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}({src:`${n}#/${r}`},y)),j=(e=>{const t=e.createElement("div");return t.style.position="fixed",t.style.top="0",t.style.left="0",t.style.padding="0",t.style.margin="0",t.style.height="100%",t.style.width="100%",t.style.zIndex="3200",t})(h);return h.body&&h.body.appendChild(j),O.init({containerElement:j}),O.on(a.a.Message,(async t=>{if("INITIATE_SPA_CLOSE"===t.type)return o&&o({currentEdition:l,targetEdition:u,upflowEvent:t.type}),t.editionChanged||t.reloadWindow?void f.location.reload():(O.cleanup(),m.a.unmountComponentAtNode(j),h.body&&h.body.removeChild(j),void i(t.flagType?g()(t,["flagType"]):void 0));"TRACK_EXPERIENCE_FAIL"===t.type&&c&&c(t.errorLocation,new Error(t.errorMessage),{currentEdition:l,targetEdition:u,
upflowEvent:t.type,errorName:t.errorName,errorLocation:t.errorLocation,errorMessage:t.errorMessage}),"TRACK_EXPERIENCE_SUCCESS"===t.type&&o&&o({currentEdition:l,targetEdition:u,upflowEvent:t.type,upflowLocation:t.location}),"START_EDITION_POLLING"===t.type&&await(async(t,n,r)=>{const i=new Date(E()+6e5).getTime(),s=async(o,a)=>{E()>i&&a(new Error("TIMEOUT"));try{const e=await Object(w.b)(`${n}/rest/internal/latest/meta/edition`),i=Object(v.e)(e[r]);if(i!==t)return void o(i);setTimeout((()=>{s(o,a)}),3e3)}catch(e){setTimeout((()=>{s(o,a)}),3e3)}};return new Promise(((e,t)=>{s(e,t)}))})(l,d,p).then((e=>{O.postMessage({type:"FINISH_EDITION_POLLING",edition:e})})).catch((e=>{O.postMessage({type:"FINISH_EDITION_POLLING",error:e instanceof Error?e.message:"Edition Polling Failed"})})),"SPA_READY"===t.type&&(y.modalElement&&(y.modalElement.style.display="flex"),s&&s(t))})),O.on(a.a.AnalyticEvent,(async e=>{const t=Object(a.e)(e),n=await Object(b.a)();Object(a.c)(n.getInstance())(t)})),O}
;var I=r("./src/packages/growth/up-flow/up-flow-iframe/src/utils.js");const R=e=>{const t=Object(h.c)(),[n,r]=Object(i.useState)(document.body?document.body.style.overflow:""),{targetEdition:s,product:a,flow:f,touchpointId:m,onClose:y,onOpen:g,subRoute:b,experienceTrackingEnabled:w=!1}=e,v=Object(l.a)(a,t),E=Object(c.stringify)({currentEdition:Object(I.a)(v),targetEdition:Object(I.a)(s),cloudId:t.cloudId,locale:t.locale,product:a,flow:f,touchpointId:m,canChangeEdition:t.isSiteAdmin}),O=Object(i.useRef)(null),j={currentEdition:v,targetEdition:s},k=Object(u.b)({experience:"upsell-edition",experienceId:m,analyticsSource:"upflowIFrame"}),_=Object(d.b)({experience:"upsell-edition",attributes:j}),x=Object(p.a)({experience:"upsell-edition",attributes:j});return Object(i.useEffect)((()=>{O.current||(w&&k(),O.current=P({src:`/gpa-up-flow/?${E}`,subRoute:b,onClose:y,onOpen:g,onSuccess:w?_:o.a,onFail:w?x:o.a,currentEdition:v,targetEdition:s,baseUrl:t.baseUrl,product:a}))
}),[y,g,_,x,v,s,t.baseUrl,a,E,b,w,k]),Object(i.useEffect)((()=>{const{body:e}=document;return null!==e&&(r(e.style.overflow),e.style.overflow="hidden"),()=>{document.body&&(document.body.style.overflow=n)}}),[n]),null};R.displayName="UpFlowIFrame";n.a=R},"./src/packages/growth/up-flow/up-flow-iframe/src/utils.js":function(e,t,n){"use strict";n.d(t,"a",(function(){return i}));var r=n("./src/packages/platform/utils/types/src/edition.js");const i=e=>e===r.a?"free":e===r.c?"standard":"premium"},"./src/packages/growth/up-flow/upflow-iframe-plugin/src/controllers/index.js":function(e,t,n){"use strict";n.d(t,"a",(function(){return l}));var r=n("./node_modules/react-sweet-state/lib/esm/store/create.js"),i=n("./node_modules/react-sweet-state/lib/esm/components/subscriber.js"),s=n("./node_modules/react-sweet-state/lib/esm/components/container.js"),o=n("./node_modules/react-sweet-state/lib/esm/components/hook.js");const a={renderUpflowModal:!1,product:"jira-software",touchpointId:"",
flow:"learn-more",onClose:()=>{},targetEdition:n("./src/packages/platform/utils/types/src/edition.js").a},c=Object(r.a)({name:"upflow-modal-store",initialState:a,actions:{mountUpflowModal:({targetEdition:e,product:t,flow:n,touchpointId:r,subRoute:i,onClose:s,onOpen:o})=>({setState:a})=>{a({targetEdition:e,product:t,flow:n,touchpointId:r,subRoute:i,onClose:s,onOpen:o,renderUpflowModal:!0})},unMountUpflowModal:()=>({setState:e})=>{e({renderUpflowModal:!1})}}}),l=(Object(i.a)(c),Object(s.a)(c),Object(o.a)(c))},"./src/packages/growth/up-flow/upflow-iframe-plugin/src/main.js":function(e,t,n){"use strict";n.r(t),n.d(t,"UpflowIframeComponent",(function(){return u}))
;var r=n("./node_modules/react/index.js"),i=n.n(r),s=n("./src/packages/platform/ui/error-boundary/src/main.js"),o=n("./src/packages/platform/observability/errors-handling/src/utils/reporting-error-boundary.js"),a=n("./src/packages/platform/services/non-critical/src/index.js"),c=n("./src/packages/growth/up-flow/up-flow-iframe/src/main.js"),l=n("./src/packages/growth/up-flow/upflow-iframe-plugin/src/controllers/index.js");const u=()=>{const[{renderUpflowModal:e,targetEdition:t,product:n,flow:r,touchpointId:u,subRoute:d,onClose:p,onOpen:h},{unMountUpflowModal:f}]=Object(l.a)();if(!e)return null;return i.a.createElement(a.b,null,i.a.createElement(s.a,{id:"upflowIframeComponent",packageName:"jiraUpflowIframePlugin"},i.a.createElement(o.a,{id:"upflowIframeComponent",packageName:"jiraUpflowIframePlugin"},i.a.createElement(c.a,{product:n,targetEdition:t,flow:r,touchpointId:u,experienceTrackingEnabled:!0,onClose:p||(()=>{f()}),subRoute:d,onOpen:h||void 0}))))}
;u.displayName="UpflowIframeComponent"}}]);try{window.__jsEvalStop("async-upflow-iframe-plugin.js")}catch(e){}
//# sourceMappingURL=https://statlas.prod.atl-paas.net/jira-frontend-source-maps/assets/async-upflow-iframe-plugin.898be9b97f014ccdeb69.8.js.map