<template>
   <div class="container">
          <div class="row">
              <div class="col-12 top">
                  <header class="col-3 header">
                    <a href="/subjects"><img class="logo" src="../../assets/logo.png" v-if="!getAdmin"></a>
                    <a href="/admin"><img class="logo" src="../../assets/logo.png" v-if="getAdmin"></a>
                  </header>
                  <nav class="col-6 navbar" v-if="getName && !getAdmin">
                    <ul>
                      <li><router-link to="/subjects">Subjects</router-link></li>
                      <li><router-link to="/groups">Groups</router-link></li>
                      <li><router-link to="/activity/add">Add activity</router-link></li>
                      <li><router-link to="/attandancelist/add">Add attendance list</router-link></li>
                    </ul>
                  </nav>
                  <nav class="col-6 navbar" v-if="getAdmin">
                    <ul>
                      <li><router-link to="/students">Students</router-link></li>
                      <li><router-link to="/professors">Professors</router-link></li>
                      <li><router-link to="/admin/add">Add admin</router-link></li>
                    </ul>
                  </nav>
                  <aside class="col-3 user" v-if="getName">
                    <span class="name">Hi, {{getName}}</span>
                    <span class="logout"><a href="" @click="logout()">Logout</a></span>
                  </aside> 
              </div>
          </div>
      </div>
</template>

<script>

import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';

export default {
  name: 'navbar',
  data () {
    return {
      //isChecked: false
    }
  },
  computed: {
    getName(){
      if(localStorage.getItem('professorName')){
        return localStorage.getItem('professorName');
      }
    },
    getAdmin(){
      if(localStorage.getItem('isAdmin')){
        return localStorage.getItem('isAdmin');
      }
    },
  },
  methods: {
    logout(){
      localStorage.clear();
    },
    stop(){
      this.isChecked = true;
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: 40px;
  color: #00529c;
  padding: 20px 0;
}
*:focus{
     outline: none;
}
.user{
  text-align: right;
}
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
.top, .navber{
  display: flex;
  align-items: center;
}
.logout{
  padding: 0 15px;
}
.logout a{
  color: red;
}
.navbar ul li{
  position: relative;
}
a {
  color: #94a08d;
}
a:before, a:after{
  width: 0;
  content: " ";
  height: 1px;
  background-color: #c4c4ef;
  right: 50%;
  transition: 1s;
  position: absolute;
  bottom: 0;
}
a:after{
  left: 50%!important;
}
a:hover{
  color: #c4c4ef;
  outline: none;
  border: none;
  text-decoration: none;
}
.navbar ul li:hover a:before{
   width: 50%;
}
.navbar ul li:hover  a:after{
  width: 50%;
}
</style>
