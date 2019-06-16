<template>
   <div class="container">
      <div class="row" v-if="groups">
        <section class="col-12">
          <h1>All groups</h1>
          <table class="table table-hover">
              <tr class="header_table">
                  <th>Id group</th>
                  <th>Name</th>
                  <th>Check group</th>
                  <th>Edit</th>
                  <th>Delete</th>
              </tr>
              <tr v-for="group in groups">
                  <td>{{group.id}}</td>
                  <td>{{group.name}}</td>
                  <td><router-link :to="'/group/' + group.id">more..</router-link></td>
                  <td><router-link class="nav-button" :to="'/group/edit/' + group.id">Edit group</router-link></td>
                  <td><button class="delete" v-on:click=deleteGroup(group.id)>&#x274C;</button></td>
              </tr>
          </table>
       </section>
       <div class="col-12 navigation_buttons">
          <ul>
              <li><router-link class="nav-button" to="/students/add">Add Student</router-link></li>
              <li><router-link class="nav-button" to="/groups/add">Add Group</router-link></li>
          </ul>
       </div>
    </div>
  </div>

</template>

<script>
  import axios from 'axios';
  export default {
    name: 'addStudent',
    data() {
      return {
          groups: []
      }
    },
    methods: {
      getGroups(){
        axios.get(this.$store.getters.getUrl + 'api/groups')
          .then(function (response) {
            this.groups = response.data.data;
            console.log(response.data);
        }.bind(this))
        .catch((error)=>{
          this.$router.push('login'); 
        });
      },
      deleteGroup(id){
         axios.delete(this.$store.getters.getUrl + 'api/groups/'+ id)
         .then(function (response) {
            alert(response.data['deleted']);
            this.getGroups();
        }.bind(this))
        .catch((error)=>{
          this.$router.push('login'); 
        });
      }
    },
    created: function () {
      if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
        this.$router.push('login'); 
      }
      this.getGroups();
    }
  }
</script>
<style scoped>

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
