<?php

namespace App\Http\Resources;

use App\Activity;
use App\Professor;
use Illuminate\Http\Resources\Json\JsonResource;

use App\Group;

class Courses extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return [
            'id' => $this->course_id,
            'name' => $this->name,
            'group' => Group::all()->where('group_id', $this->group_id)->toArray(),
            'professor' => Professor::all()->where('id', $this->professor_id)->toArray(),
            'activities' => Activity::all()->where('course_id', $this->course_id)->toArray(),
        ];
    }
}
