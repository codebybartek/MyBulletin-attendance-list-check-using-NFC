<template>
   <div class="container">
      <div class="row" v-if="subjects">
        <section class="col-12">
          <h1>Subjects</h1>
          <table class="table table-hover">
              <tr class="header_table">
                  <th>Id</th>
                  <th>Name</th>
                   <th>Edit</th>
                  <th>Delete</th>
              </tr>
              <tr v-for="subject in subjects">
                  <td>{{subject.subject_id}}</td>
                  <td><router-link class="link" :to="'/courses/' + subject.subject_id">{{subject.name}}</router-link></td>
                  <td><router-link class="nav-button" :to="'/subject/edit/' + subject.subject_id">Edit subject</router-link></td>
                  <td><button class="delete" v-on:click=deleteSubject(subject.subject_id)>&#x274C;</button></td>
              </tr>
          </table>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
              <li><router-link class="nav-button" to="/subjects/add">Add Subject</router-link></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'subjects',
  data () {
    return {
      subjects: [],
      refresh: true
    }
  },
  methods: {
      getSubjects(){
          axios.get(this.$store.getters.getUrl + 'api/subjects')
        .then(function (response) {
            this.subjects = response.data.data;
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      deleteSubject(id){
         axios.delete(this.$store.getters.getUrl + 'api/subjects/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getSubjects();
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      }
  },
  created: function () {
    if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
      this.$router.push('/login'); 
    }
    this.getSubjects();
    if(localStorage.getItem('refresh') == 0){
      localStorage.setItem('refresh', 1);
      this.$router.go(0);
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
