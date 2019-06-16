<?php

namespace App\Http\Resources;

use App\Http\Resources\Courses as CoursesResource;
use App\Professor;
use App\Student;
use App\Course;
use Illuminate\Http\Resources\Json\JsonResource;
use App\StudentsGroup;
use App\Http\Resources\StudentsGroups as StudentsGroupsResource;

class Groups extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        $professor_id = Course::all()->where('group_id', $this->group_id)->pluck('professor_id');
        $professors = [];
        foreach($professor_id as $id) {
            $professor = Professor::all()->where('id', $id);
            $professors[$id]['name'] = $professor->pluck('name');
        }
        $studentsGroup = StudentsGroup::all()->where('group_id', $this->group_id);
        $students = StudentsGroupsResource::collection($studentsGroup);
        return [
            'id' => $this->group_id,
            'name' => $this->name,
            'professor' =>  $professors,
            'students' => $students
        ];
    }
}
