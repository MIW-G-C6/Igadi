try{window.performance.mark("async-legacy-issue-create-trackers.js:eval-start")}catch(e){}(window.webpackJsonp=window.webpackJsonp||[]).push([["async-legacy-issue-create-trackers"],{"./src/packages/issue-create/legacy-issue-create-trackers/src/index.js":function(e,r,t){"use strict";t.r(r);var a=t("./node_modules/react/index.js"),s=(t("./node_modules/react-magnetic-di/lib/esm/index.js"),t("./src/packages/common-legacy-do-not-add-anything-new/src/bridge/index.js")),c=t("./src/packages/platform/observability/errors-handling/src/utils/fire-error-analytics.js"),n=t("./node_modules/@atlaskit/analytics-next/dist/es2019/hooks/useAnalyticsEvents.js"),i=t("./src/packages/platform/observability/product-analytics-bridge/src/utils/fire-analytics.js");function o(e,r){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);r&&(a=a.filter((function(r){return Object.getOwnPropertyDescriptor(e,r).enumerable}))),t.push.apply(t,a)}return t}function u(e,r,t){
return r in e?Object.defineProperty(e,r,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[r]=t,e}r.default=()=>{const{createAnalyticsEvent:e}=Object(n.a)();return Object(a.useEffect)((()=>{Object(s.a)({name:"jira/util/events",wrmKeys:"wrc!jira.webresources:jira-events"}).then((r=>{r.bind("legacyIssueCreated",((r,t)=>{const a=e({});Object(i.f)(a,"issueCreated inProductSurvey",t.objectId,function(e){for(var r=1;r<arguments.length;r++){var t=null!=arguments[r]?arguments[r]:{};r%2?o(Object(t),!0).forEach((function(r){u(e,r,t[r])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):o(Object(t)).forEach((function(r){Object.defineProperty(e,r,Object.getOwnPropertyDescriptor(t,r))}))}return e}({},t.attributes))}))})).catch((e=>{Object(c.a)({meta:{id:"LegacyIssueCreateTrackers",packageName:"jiraLegacyIssueCreateTrackers",teamName:"gryffindor"},error:e})}))}),[e]),null}}}]);try{window.__jsEvalStop("async-legacy-issue-create-trackers.js")
}catch(e){}
//# sourceMappingURL=https://statlas.prod.atl-paas.net/jira-frontend-source-maps/assets/async-legacy-issue-create-trackers.98cd9c30f33a867d553c.8.js.map