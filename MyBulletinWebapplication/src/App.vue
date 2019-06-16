<template>
  <div id="app">
    <navbar />
    <router-view />
  </div>
</template>

<script>

import navbar from './components/static/navbar'

import courses from './components/courses'

export default {
  name: 'App',
  components: {
        navbar,
  }
  }
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 30px;
}
.logo{
    max-width: 350px;
    float: left;
}
h1, h2 {
    font-size: 50px;
    text-align: center;
    width: 100%;
    margin: 40px 0;
}
h3,h4{
  text-align: left;
}
.navigation_buttons{
  padding: 20px 0;
}
.nav-button{
  border: 1px solid #c4c4ef;
  border-radius: 30px;
  background-color: transparent;
  padding: 10px 50px;
  margin-top: 20px;
  transition: 0.4s;
  outline: none;
  cursor: pointer;
}
.nav-button:focus, .nav-button:active, .nav-button:hover{
  border-color: #00529c;
  color: #00529c;
  text-decoration: none;
}
.header_table{
  border-bottom: 1px solid #c4c4ef;
  border-top: 1px solid #c4c4ef;
  background-color: #fffbd2;
}
.button-primary{
  border: 1px solid #c4c4ef;
  border-radius: 30px;
  background-color: transparent;
  padding: 10px 50px;
  margin-top: 20px;
  transition: 0.4s;
  outline: none;
  cursor: pointer;
}
input, select{
  width: 100%;
  max-width: 400px;
  display: block;
  margin: 0 auto;
  padding: 10px;
  border: 1px solid #c4c4ef;
  border-radius: 30px;
  text-align: center;
  outline: none;
}
.courseId, .activity input, .activity select{
  margin: 0 auto!important;
  margin-bottom: 20px!important;
}
.time input, .duration input{
  margin-bottom: 0!important;
}
.v-calendar .input-field svg{
  top: 34%!important;
}
input[type=number][data-v-0a823cf0]{
  padding: 0;
  width: auto;
  margin: 0;
  display: inline-block!impoertant;
}
.datetime input{
  display: inline-block!important;
  width: auto!important;
}
.button-primary:focus, .button-primary:active, .button-primary:hover{
  border-color: #00529c;
  color: #00529c;
}
table tr{
 margin-top: 15px;
}
table td{
  border: none;
  border-bottom: 1px solid #c4c4ef;
  border-radius: 30px;
}
.table th, .table td{
  vertical-align: middle!important;
}
ul {
  list-style-type: none;
  padding: 0;
}
/*li {
  display: inline-block;
  margin: 0 10px;
}*/
.link {
  color: #c4c4ef;
}
.link:hover{
  color: #c4c4ef;
  outline: none;
  text-decoration: none;
}
.top-50{
  margin-top: 50px;
}
a {
  color: #42b983;
}
.delete{
  background: transparent;
  border: none;
  border-radius: 100%;
  width: 35px;
  height: 35px;
  font-size: 16px;
  text-align: center;
  vertical-align: middle;
}
@media(max-width:767px){
  body{
    font-size: 10px;
  }
  h1, h2 {
    font-size: 30px;
    margin: 20px 0;
  }
  h3, h4{
    font-size: 18px;
  }
  .logo {
    max-width: 80px;
  }
  .navbar, .user{
    padding: 0;
  }
  .v-calendar .calendar{
    position: relative;
    width: 100%;
    display: block;
  }
  .v-calendar .content{
    position: relative!important;
    width: 100%!important;
    display: block!important;
  }
  .nav-button{
    padding: 10px 20px;
  }
}
</style>
