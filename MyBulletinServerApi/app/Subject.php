<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Subject extends Model
{
    protected $fillable = ['name', 'professor_id'];
    protected $primaryKey = "subject_id";
}
