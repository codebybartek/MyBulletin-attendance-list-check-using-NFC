<template>
   <div class="container">
      <div class="row">
        <div class="col-12">
          <h1>Edit group: </h1>
          <div class="form-login align-center">      
              <span class="success">{{success}}</span>             
              <input v-model="group.name" type="text" placeholder="Subject name" id="name"/>
              <span class="error" v-for="error in errors">{{error}}</span>
              <button class="u-full-width button-primary" type="submit" v-on:click="saveGroup()" >Edit group</button>
          </div>
       </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'editGroup',
  data () {
    return {
      course_id: this.$route.params.id,
      errors: [],
      success: '',
      group: {
        name: '',
        group_id: parseInt(this.$route.params.id),
        _method: "PUT"
      }
    }
  },
  methods: {
    saveGroup() {
        axios.post(this.$store.getters.getUrl + 'api/groups/' + this.group.group_id, this.group)
        .then((response) =>{
          alert(response.data['updated']);
          getGroup(this.group.group_id);
        })
        .catch((error)=>{
         // this.$router.push('login'); 
        });

    },
    getGroup(id) {
      axios.get(this.$store.getters.getUrl + 'api/groups/' + this.group.group_id + '/edit')
       .then(function (response) {
          this.group.name = response.data[0].name;
        }.bind(this))
        .catch((error)=>{
          this.$router.push('/groups'); 
          alert('You could only edid group that you create')
        });
    }
  },
  created: function () {
    if(!localStorage.getItem('token') || localStorage.getItem('isAdmin') == 1){
      this.$router.push('/login'); 
    }
    this.getGroup(this.group.group_id);
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3,h4{
  text-align: left;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
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
  margin-bottom: 15px;
  outline: none;
}
 #name:focus, #name:active{
  border-color: #00529c;
}
a {
  color: #42b983;
}
</style>
