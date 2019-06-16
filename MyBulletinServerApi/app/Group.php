<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Group extends Model
{
    protected $fillable = ['name', 'professor_id'];
    protected $primaryKey = "group_id";
}
