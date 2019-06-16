<template>
   <div class="container">
        <div class="row" v-if="group">
            <h1>Group name: {{group.name}}</h1>
            <h4 v-for="prfessor in professors">Professor: {{prfessor.name[0]}}</h4>
            <table class="table table-hover">
                <tr class="header_table">
                    <th>Id</th>
                    <th>Name</th>
                    <th>Tag id</th>
                    <th>Delete</th>
                </tr>
                <tr v-for="student in students">
                    <td>{{student.student_id}}</td>
                    <td>{{student.name}}</td>
                    <td>{{student.tagId}}</td>
                    <td><button class="delete" v-on:click=deleteStudent(student.student_id)>&#x274C;</button></td>
                </tr>
            </table>
        </div>
    </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'group',
  data () {
    return {
      id: this.$route.params.id,
      group: null,
      students: [],
      professors: []
    }
  },
  methods: {
      getGroups(){
        axios.get(this.$store.getters.getUrl + 'api/groups/'+ this.id)
         .then(function (response) {
            this.group = response.data.data[0];
            this.students =response.data.data[0].students;
            this.professors =response.data.data[0].professor;
            console.log(response.data.data[0]);
        }.bind(this))
        .catch((error)=>{
          this.$router.push('login'); 
        });
      },
      deleteStudent(id){
         axios.delete(this.$store.getters.getUrl + 'api/studentsgroup/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getGroups();
        }.bind(this))
        .catch((error)=>{
          //this.$router.push('/login'); 
        });
      }
  },
  created: function () {
    if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
      this.$router.push('/login'); 
    }
    this.getGroups();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3,h4{
  text-align: left;
}
h4{
  display: block;
  width: 100%;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
