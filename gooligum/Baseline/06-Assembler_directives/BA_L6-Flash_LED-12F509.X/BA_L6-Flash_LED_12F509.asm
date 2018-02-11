;************************************************************************
;                                                                       *
;   Filename:      BA_L6-Flash_LED-12F509.asm                  *
;   Date:          02/10/2018                                           *
;   File Version:  1.1                                                  *
;                                                                       *
;   Author:        Mike Rocha                                           *
;   Company:       RoCo, Inc.                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Architecture:  Baseline PIC                                         *
;   Processor:     12F508/509                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Files required:                                                     *
;                                                                       *
;************************************************************************
;                                                                       *
;   Description:    Lesson 6, p. 1 - 2a                                 *
;                                                                       *
;   Demonstrates use of arithmetic operators in constant expresssion    *
;                                                                       *
;   LED flashes at 1 Hz (50% duty cycle),                               *
;   with timing derived from internal 4 MHz oscillator                  *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = flashing LED                                              *
;                                                                       *
;************************************************************************

	list      p=12F509            ; list directive to define processor
	#include <p12F509.inc>        ; processor specific variable definitions
	
	radix     dec

	__CONFIG   _MCLRE_ON & _CP_OFF & _WDT_OFF & _IntRC_OSC

; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.

;***** VARIABL DEFINITIONS *****************************************************
	UDATA
sGPIO   res 1                     ; shadow copy of GPIO
dc1     res 1                     ; loop counter - inner
dc2     res 1                     ; loop counger - outer
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x3FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration 

; Internal RC calibration value is placed at location 0xFF by Microchip
; as a movlw k, where the k is a literal value.
		
;***** MAIN PROGRAM ************************************************************
	
;***** Initialization
start	
	movlw   b'111101'         ; configure GP1 (only) as an output
	tris    GPIO
	
	clrf    sGPIO             ; clear shadow register
	
main_loop
	; toggle LED on GP1
	movf    sGPIO,w           ; get the shadow copy of GPIO
	xorlw   b'000010'         ; toggle the W bit corresponding to the GP1 (bit 1)
	movwf   sGPIO             ;   in shadow register
	movwf   GPIO              ; and write to GPIO
	
	; delay 500 ms
	movlw   500000 / ((1023 * 1023 + 2) + 2)              
	;;;movlw   .244              ; loop (outer) 244 x (1023 + 1023 + 3) + 2
	movwf   dc2               ;   = 499,958 cyclews
	clrf    dc1               ; inner loop: 256 x 4 - 1
dly1    nop                       ; inner loop 1 = 256 x 4 -1 = 1023 cycles
	decfsz  dc1,f
	goto    dly1
dly2    nop                       ; inner loop 2 = 1023 cycles
	decfsz  dc1,f
	goto    dly2
	decfsz  dc2,f
	goto    dly1

	goto    main_loop         ; repeat forever
	
;***** Main loop
	goto $                    ; loop forever

	END                       ; directive 'end of program'

