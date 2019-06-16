<template>
   <div class="container">
      <div class="row" v-if="students">
        <section class="col-12">
          <h1>Students</h1>
          <table class="table table-hover">
              <tr class="header_table">
                  <th>Id</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>TagId</th>
                  <th>Edit</th>
                  <th>Delete</th>
              </tr>
              <tr v-for="student in students">
                  <td>{{student.student_id}}</td>
                  <td>{{student.name}}</td>
                  <td>{{student.email}}</td>
                  <td>{{student.tagId}}</td>
                  <td><router-link class="nav-button" :to="'/student/edit/' + student.student_id">Edit</router-link></td>
                  <td><button class="delete" v-on:click=deleteStudent(student.student_id)>&#x274C;</button></td>
              </tr>
          </table>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
              <li><router-link class="nav-button" to="/student/add">Add Student</router-link></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'adminStudent',
  data () {
    return {
      students: []
    }
  },
  methods: {
      getStudents(){
          axios.get(this.$store.getters.getUrl + 'api/students')
        .then(function (response) {
            this.students = response.data;
            if(response.data =="unauthorized"){
              this.$router.push('/login'); 
            }
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      deleteStudent(id){
         axios.delete(this.$store.getters.getUrl + 'api/students/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getStudents();
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      }
  },
  created: function () {
    if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') != 1){
      this.$router.push('/login'); 
    }
    this.getStudents();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
