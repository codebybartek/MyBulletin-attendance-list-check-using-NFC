<template>
   <div class="container">
      <div class="row" v-if="professors">
        <section class="col-12">
          <h1>Professors</h1>
          <table class="table table-hover">
              <tr class="header_table">
                  <th>Id</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Tag id</th>
                  <th>Edit</th>
                  <th>Delete</th>
              </tr>
              <tr v-for="professor in professors" v-if="professor.isAdmin != true">
                  <td>{{professor.id}}</td>
                  <td>{{professor.name}}</td>
                  <td>{{professor.email}}</td>
                  <td>{{professor.tagId}}</td>
                  <td><router-link class="nav-button" :to="'/professor/edit/' + professor.id">Edit</router-link></td>
                  <td><button class="delete" v-on:click=deleteProfessor(professor.id)>&#x274C;</button></td>
              </tr>
          </table>
        </section>
        <div class="col-12 navigation_buttons">
          <ul>
              <li><router-link class="nav-button" to="/professor/add">Add Professor</router-link></li>
          </ul>
       </div>
      </div>
    </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'adminProfessor',
  data () {
    return {
      professors: []
    }
  },
  methods: {
      getProfessors(){
          axios.get(this.$store.getters.getUrl + 'api/professors')
        .then(function (response) {
            this.professors = response.data;
            if(response.data =="unauthorized"){
              this.$router.push('/login'); 
            }
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/login'); 
        });
      },
      deleteProfessor(id){
         axios.delete(this.$store.getters.getUrl + 'api/professors/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getProfessors();
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
    this.getProfessors();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
