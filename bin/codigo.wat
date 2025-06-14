(module
(type $_sig_i32i32i32 (func (param i32 i32 i32) ))
(type $_sig_i32ri32 (func (param i32) (result i32)))
(type $_sig_i32 (func (param i32)))
(type $_sig_ri32 (func (result i32)))

(type $_sig_void (func ))
(import "runtime" "exceptionHandler" (func $exception (type $_sig_i32)))

(import "runtime" "print" (func $printi32 (type $_sig_i32)))
(import "runtime" "read" (func $readi32 (type $_sig_ri32)))

(memory 2000)   ;; TODO: Esto se cambia imagino
(global $SP (mut i32) (i32.const 0)) ;; start of stack
(global $MP (mut i32) (i32.const 0)) ;; mark pointer
(global $NP (mut i32) (i32.const 131071996)) ;; heap 2000*64*1024-4
(start $principal)
(func $principal (type $_sig_void)
	(local $localsStart i32)
	(local $temp i32)
	i32.const 4
	call $reserveStack
	local.set $temp
	global.get $MP
	local.get $temp
	i32.store
	global.get $MP
	i32.const 4
	i32.add
	local.set $localsStart



	call $main
	i32.load
	call $printi32
	call $freeStack
)
(func $main (result i32)
	(local $localsStart i32)
	(local $temp i32)
	i32.const 40
	call $reserveStack
	local.set $temp
	global.get $MP
	local.get $temp
	i32.store
	global.get $MP
	i32.const 4
	i32.add
	local.set $localsStart

	i32.const 0
	i32.const 0
	i32.const 0
	i32.const 0
	i32.const 0
	i32.const 1
	i32.const -17
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 0
	i32.add
	call $invStore
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 4
	i32.add
	call $invStore
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 8
	i32.add
	call $invStore
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 12
	i32.add
	call $invStore
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 16
	i32.add
	call $invStore
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 20
	i32.add
	call $invStore
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 24
	i32.add
	call $invStore

	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 0
	i32.add
	i32.load
	call $printi32

	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 4
	i32.add
	i32.load
	call $printi32

	i32.const 8
	i32.const 6
	i32.const 4
	i32.const 2
	i32.const 0
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 8
	i32.add
	i32.const 0
	i32.add
	call $invStore
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 8
	i32.add
	i32.const 4
	i32.add
	call $invStore
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 8
	i32.add
	i32.const 8
	i32.add
	call $invStore
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 8
	i32.add
	i32.const 12
	i32.add
	call $invStore
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 8
	i32.add
	i32.const 16
	i32.add
	call $invStore

	i32.const 28
	local.get $localsStart
	i32.add
	i32.const 0
	i32.store
	block
	 loop
	i32.const 28
	local.get $localsStart
	i32.add
	i32.load
	i32.const 5
	i32.lt_s
 i32.eqz
 br_if 1
	i32.const 28
	local.get $localsStart
	i32.add
	i32.load
	i32.const 16
	i32.ge_s
	if
	i32.const 1
	call $exception
	end
	i32.const 28
	local.get $localsStart
	i32.add
	i32.load
	i32.const 0
	i32.lt_s
	if
	i32.const 1
	call $exception
	end
	i32.const 0
	local.get $localsStart
	i32.add
	i32.const 8
	i32.add
	i32.const 28
	local.get $localsStart
	i32.add
	i32.load
	i32.const 4
	i32.mul
	i32.add
	i32.load
	call $printi32
	i32.const 28
	local.get $localsStart
	i32.add
	i32.const 28
	local.get $localsStart
	i32.add
	i32.load
	i32.const 2
	i32.add
	i32.store
	 br 0
	 end
	end

	global.get $SP
	i32.const 4
	i32.sub
	i32.const 0
	i32.store
	global.get $SP
	i32.const 4
	i32.sub
	call $freeStack
	return
)




 ;;FUNCIONES DEFINIDAS
(func $reserveStack (param $size i32)
   (result i32)
   global.get $MP
   global.get $SP
   global.set $MP
   global.get $SP
   local.get $size
   i32.add
   global.set $SP
   global.get $SP
   global.get $NP
   i32.gt_u
   if
   i32.const 3
   call $exception
   end
)
(func $freeStack (type $_sig_void)
   global.get $MP
   global.set $SP
   global.get $MP
   i32.load
   global.set $MP   
)
(func $reserveHeap (type $_sig_i32)
   (param $size i32)
   global.get $NP
   local.get $size
   i32.sub
   global.set $NP
   global.get $SP
   global.get $NP
   i32.gt_u
   if
   i32.const 3
   call $exception
   end

)
(func $copyn (type $_sig_i32i32i32) ;; copy $n i32 slots from $src to $dest
   (param $src i32)
   (param $dest i32)
   (param $n i32)
   block
     loop
       local.get $n
       i32.eqz
       br_if 1
       local.get $n
       i32.const 1
       i32.sub
       local.set $n
       local.get $dest
       local.get $src
       i32.load
       i32.store
       local.get $dest
       i32.const 4
       i32.add
       local.set $dest
       local.get $src
       i32.const 4
       i32.add
       local.set $src
       br 0
     end
   end
)




(func $invStore (param $elem i32) (param $dir i32) 
	local.get $dir
	local.get $elem
	i32.store
)

(export "memory" (memory 0))
(export "init" (func $principal))
)
