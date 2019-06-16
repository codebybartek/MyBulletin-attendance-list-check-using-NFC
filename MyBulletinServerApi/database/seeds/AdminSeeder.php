<?php

use Illuminate\Database\Seeder;

class AdminSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('professors')->insert([
            'name' => "admin",
            'email' => "admin@admin.es",
            'password' => bcrypt('admin'),
            'tagId' => "admin",
            'mobile_token' => "admin",
            'isAdmin' => true
        ]);
    }
}
