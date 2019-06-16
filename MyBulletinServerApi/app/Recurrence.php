<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Recurrence extends Model
{
    protected $fillable = ['dateStart', 'dateEnd', 'everyDay'];
}
